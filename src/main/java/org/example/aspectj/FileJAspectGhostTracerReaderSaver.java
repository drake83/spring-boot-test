package org.example.aspectj;

import org.apache.commons.io.FilenameUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.example.aspectj.configuration.GhostConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@Aspect
@ConditionalOnExpression(value = "${ghost.tracer.file.enabled:false} and ${ghost.tracer.enabled:false}")
public class FileJAspectGhostTracerReaderSaver extends AAspectJAspectJGhostTracerAspectReader {

    private static final Logger LOG = LoggerFactory.getLogger(FileJAspectGhostTracerReaderSaver.class);

    @Autowired
    private GhostConfiguration configuration;


    @Override
    public String read(String key) {
        LOG.info("File Reading....");
        return Optional.of(createFileFromKey(key)).filter(File::exists).map(a -> {
            LOG.debug("Exists? " + a.exists());
            return a;
        }).map(f -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                return reader.lines().collect(Collectors.joining());
            } catch (Exception e) {
                LOG.error("Read exception ", e);
                throw new RuntimeException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Ghosting File with Key " + key + " not found!"));

    }

    @Override
    public void save(String key, String result, String connector, String request, String queryString) {
        LOG.info("Saving response to File....");
        File f = createFileFromKey(key);
        try (Writer fw = new BufferedWriter(new FileWriter(f))) {
            fw.write(result);
        } catch (IOException e) {
            LOG.error("Save exception ", e);
            throw new RuntimeException(e);
        }
    }

    private File createFileFromKey(String key) {
        return new File(FilenameUtils.concat(configuration.getDataPath(), key + ".json"));
    }


    @Override
    protected Predicate<ProceedingJoinPoint> isRecording(ProceedingJoinPoint pjp) {
        return ghostTracer -> configuration.isFileGhostRecordingEnabled();
    }

    @Override
    protected Predicate<ProceedingJoinPoint> isReading(ProceedingJoinPoint jpjp) {
        return ghostTracer -> configuration.isFileGhostReadingEnabled();
    }

    @Override
    protected Predicate<ProceedingJoinPoint> isFeatureEnabled(ProceedingJoinPoint jpj) {
        return ghostTracer -> configuration.isFileTraceEnabled();
    }
}
