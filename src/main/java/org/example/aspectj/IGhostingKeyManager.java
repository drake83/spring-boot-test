package org.example.aspectj;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import org.aspectj.lang.ProceedingJoinPoint;
import org.example.aspectj.annotations.IgnoreInputAttributeTable;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IGhostingKeyManager extends IBaseGhostingKeyManager, IGhostingHashingManager {


    default Optional<List<IgnoreInputAttributeTable>> ignoreInputAttributeClass(IgnoreInputAttributeTable[] ignoreInputAttributesClass) {
        return Optional.ofNullable(ignoreInputAttributesClass).filter(ia -> ia.length > 0).map(Arrays::asList);
    }

    default String urlEncodeUTF8(String value) {
        return Try.of(() -> URLEncoder.encode(value, StandardCharsets.UTF_8.toString())).getOrElseThrow(() -> new RuntimeException("Error with encoding quey param " + value + ": Not able to encode"));
    }


    default Optional<String> fromQueryParamsToString(Map<String, Object> queryParams) {
        return queryParams.entrySet().stream().map(p -> urlEncodeUTF8(p.getKey()) + "=" + urlEncodeUTF8(p.getValue().toString())).reduce((p1, p2) -> p1 + "&" + p2).map(p -> "?" + p);
    }

    default String createKey(ProceedingJoinPoint pjp, Map<String, Object> queryParams, Object input, ObjectMapper om, Class<?> connectorClass, IgnoreInputAttributeTable[] ignoreInputAttributesClass) {
        String requestObjectToString = jacksonObjectToString(input, om, ignoreInputAttributesClass);
        String aggregatedInputs = fromQueryParamsToString(queryParams).map(queryParamsAsString -> queryParamsAsString + requestObjectToString).orElse(requestObjectToString);
        String reworkedConnectorClass = Optional.ofNullable(connectorClass).map(Class::getName).orElseGet(() -> pjp.getSignature().getDeclaringType().getName());
        return concatForKey(reworkedConnectorClass, aggregatedInputs);
    }

    default String createKey(ProceedingJoinPoint pjp, String inputAsString, Class<?> connectorClass) {
        String reworkedConnectorClass = Optional.ofNullable(connectorClass).map(Class::getName).orElseGet(() -> pjp.getTarget().getClass().getName());
        return concatForKey(reworkedConnectorClass, inputAsString);
    }

    default String jacksonObjectToString(Object input, ObjectMapper om, IgnoreInputAttributeTable[] ignoreInputAttributesClass) {
        return Optional.ofNullable(input).map(a -> ignoreInputAttributeClass(ignoreInputAttributesClass).map(ia -> {
            ObjectMapper omcopied = om.copy();
            ia.forEach(iaa -> omcopied.addMixIn(iaa.sourceClass(), iaa.mixinClass()));
            try {
                return omcopied.writeValueAsString(a);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).orElseGet(() -> {
            try {
                return om.writeValueAsString(a);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        })).orElse(null);
    }


}
