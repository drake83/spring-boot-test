package org.example.aspectj;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import  org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.aspectj.annotations.GhostTracerReader;
import org.example.aspectj.annotations.GhostTracerSaver;
import org.example.aspectj.annotations.IgnoreInputAttributeTable;
import org.example.aspectj.configuration.GhostConfiguration;
import org.example.aspectj.model.IRestRequestTransformer;
import org.example.aspectj.model.IRestResponseTransformer;
import org.example.aspectj.model.RestConnectorResponse;
import org.example.aspectj.model.RestConnectorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Component
public abstract class AAspectJAspectJGhostTracerAspectReader implements IAspectJGhostTracerAspectReader, IAspectJGhostTracerAspectSaver, IGhostingKeyManager {

    private static final Logger LOG = LoggerFactory.getLogger(AAspectJAspectJGhostTracerAspectReader.class);
    @Autowired
    private GhostConfiguration configuration;

    @Autowired
    protected ObjectMapper om;

    protected abstract Predicate<ProceedingJoinPoint> isRecording(ProceedingJoinPoint jpj);

    protected abstract Predicate<ProceedingJoinPoint> isReading(ProceedingJoinPoint jpj);

    protected abstract Predicate<ProceedingJoinPoint> isFeatureEnabled(ProceedingJoinPoint jpj);

    private final Predicate<ProceedingJoinPoint> isGloballyEnabled = ghostTracer -> configuration.isGloballyEnabled();

    @Around("execution (* * (..)) && @annotation(gt)")
    public final Object readerAdvice(ProceedingJoinPoint pjp, GhostTracerReader gt) {
        return executor(pjp, isReading(pjp).and(isGloballyEnabled).and(isFeatureEnabled(pjp)), () -> ghostingReader(pjp, gt.connectorClass(), gt.ignoreInputAttributesClass(), gt.restConnectorReturnType(), gt.restConnectorGenericReturnType()));
    }


    @Around("execution (* * (..)) && @annotation(gt)")
    public final Object saverAdvice(ProceedingJoinPoint pjp, GhostTracerSaver gt) {
        return executor(pjp, isRecording(pjp).and(isGloballyEnabled).and(isFeatureEnabled(pjp)), () -> ghostingSaver(pjp, gt.connectorClass(), gt.ignoreInputAttributesClass()));
    }

    private Object executor(ProceedingJoinPoint pjp, Predicate<ProceedingJoinPoint> filter, Supplier<Object> ghosting) {
        return Optional.ofNullable(pjp).filter(filter).map(a -> ghosting.get())
                .orElseGet(proceed.apply(pjp));
    }


    protected final Object ghostingReader(ProceedingJoinPoint pjp, Class<?> connectorClass, IgnoreInputAttributeTable[] ignoreInputAttributesClass, Class<?> restConnectorReturnType, Class<?> restConnectorGenericReturnType) {
        LOG.info("TRIGGERED READER ADVICE....");
        Tuple2<Map<String, Object>, Object> input = extractRequestParamsAndRequestBody(pjp);
        String key = createKey(pjp, input._1(), input._2(), om, connectorClass, ignoreInputAttributesClass);
        LOG.info("TRIGGERED READER key " + key);
        String hashedKey = hashString(key);
        LOG.info("TRIGGERED READER hashedKey " + hashedKey);
        String ghostedResp = read(hashedKey);
        Signature signature = pjp.getSignature();
        Class returnType = ((MethodSignature) signature).getReturnType();

        List<Object> args = Optional.of(pjp).map(JoinPoint::getArgs).filter(a -> Arrays.stream(a).anyMatch(Objects::nonNull)).map(Arrays::asList).orElse(Collections.emptyList());
        return Optional.of(args).filter(a -> !a.isEmpty()).filter(a -> a.size() >= 2).map(a -> a.get(2)).filter(a -> a instanceof IRestResponseTransformer).map(a -> (IRestResponseTransformer) a).map(a -> {
            LOG.info("TRIGGERED READER transforming ghost trace response using ghosted resp {}", ghostedResp);
            RestConnectorResponse<Object> response = new RestConnectorResponse();
            if (restConnectorGenericReturnType.getTypeName().equals("java.lang.Void")) {
                response.setResponse(ResponseEntity.ok(deserializeFromString(ghostedResp, restConnectorReturnType)));
            } else {
                response.setResponse(ResponseEntity.ok(deserializeFromString(ghostedResp, restConnectorReturnType, restConnectorGenericReturnType)));
            }
            return a.transform(response);
        }).orElseGet(() -> deserializeFromString(ghostedResp, returnType));


    }

    private static Tuple2<Map<String, Object>, Object> extractRequestParamsAndRequestBody(ProceedingJoinPoint pjp) {
        List<Object> args = Optional.of(pjp).map(JoinPoint::getArgs).filter(a -> Arrays.stream(a).anyMatch(Objects::nonNull)).map(Arrays::asList).orElse(Collections.emptyList());
        Object input = Optional.of(args).filter(a -> !a.isEmpty()).map(a -> a.get(0)).orElse(null);
        Object[] argss = Optional.of(args).filter(a -> a.size() >= 4).map(a -> (Object[]) a.get(3)).orElse(null);
        Object inputReworked = Optional.of(args).filter(a -> !a.isEmpty()).filter(a -> a.size() >= 2).map(a -> a.get(1)).filter(a -> a instanceof IRestRequestTransformer).map(a -> (IRestRequestTransformer) a).map(a -> {
            LOG.info("TRIGGERED READER transforming input {} and args {} ", input, argss);
            return a.transform(input, argss);
        }).map(RestConnectorRequest::getRequest).orElse(input);
        Map<String, Object> queryString = Optional.of(args).filter(a -> a.size() >= 2).map(a -> a.get(1)).filter(a -> a instanceof IRestRequestTransformer).map(a -> (IRestRequestTransformer) a).map(a -> a.transform(input, argss)).map(a -> a.getQueryParams()).orElse(Collections.emptyMap());
        return Tuple.of(queryString, inputReworked);
    }

    protected final Object ghostingSaver(ProceedingJoinPoint pjp, Class<?> connectorClass, IgnoreInputAttributeTable[] ignoreInputAttributesClass) {
        LOG.info("TRIGGERED SAVER ADVICE....");
        Tuple2<Map<String, Object>, Object> input = extractRequestParamsAndRequestBody(pjp);
        String inputAsString = jacksonObjectToString(input._2(), om, ignoreInputAttributesClass);
        String queryString = fromQueryParamsToString(input._1()).orElse(null);
        String finalInputs = fromQueryParamsToString(input._1()).map(a -> a + inputAsString).orElse(inputAsString);
        String hashedKey = hashString(createKey(pjp, finalInputs, connectorClass));
        LOG.info("TRIGGERED SAVER hashedKey " + hashedKey);
        return callAndSave(pjp, hashedKey, connectorClass.getName(), inputAsString, queryString);
    }

    protected final Function<ProceedingJoinPoint, Supplier<Object>> proceed = pjp -> () -> {
        try {
            return pjp.proceed(pjp.getArgs());
        } catch (Throwable e) {
            LOG.error("Proceed exception ", e);
            throw new RuntimeException(e);
        }
    };

    protected final Object callAndSave(ProceedingJoinPoint pjp, String key, String connectorName, String requestBody, String queryString) {
        try {
            LOG.debug("Call and save");
            Object result = proceed.apply(pjp).get();
            LOG.info("result {}", result);
            saveAsync(key, om.writeValueAsString(result), connectorName, requestBody, queryString);
            return result;
        } catch (Throwable e) {
            LOG.error("call save ", e);
            throw new RuntimeException(e);
        }
    }

    private void saveAsync(String key, String result, String connectorName, String request, String queryString) {
        CompletableFuture.runAsync(() -> save(key, result, connectorName, request, queryString));
    }


    protected final Object deserializeFromString(String resultJson, Class<?> returnClazz, Class<?> restConnectorGenericReturnType) {
        LOG.info("returnClazz {}", returnClazz.getName());
        return Optional.ofNullable(returnClazz).map(a -> omReadValue(a, resultJson, restConnectorGenericReturnType)).orElseThrow(RuntimeException::new);
    }

    protected final Object deserializeFromString(String resultJson, Class<?> returnClazz) {
        LOG.info("returnClazz {}", returnClazz.getName());
        return Optional.ofNullable(returnClazz).map(a -> omReadValue(a, resultJson)).orElseThrow(RuntimeException::new);
    }

    private Object omReadValue(Class<?> returnClazz, String resultJson) {
        try {
            return om.readValue(resultJson, returnClazz);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Object omReadValue(Class<?> returnClazz, String resultJson, Class<?> restConnectorGenericReturnType) {
        try {
            return om.readValue(resultJson, om.getTypeFactory().constructCollectionType((Class<? extends Collection>) restConnectorGenericReturnType, returnClazz));
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
