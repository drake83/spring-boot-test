package org.example.aspectj.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class GhostConfiguration {

    @Getter
    @Value("${ghost.tracer.file.localPath:./data}")
    private String dataPath;

    @Value("${ghost.tracer.file.recording:false}")
    private String fileRecording;

    @Value("${ghost.tracer.file.reading:false}")
    private String fileReading;

    @Value("${ghost.tracer.file.enabled:false}")
    private String fileEnabled;


    @Getter
    @Value("${ghost.tracer.enabled:false}")
    private String enabled;


    public Boolean isFileGhostRecordingEnabled() {
        return Optional.of(Boolean.valueOf(fileRecording)).filter(fr -> fr.equals(Boolean.TRUE)).orElse(Boolean.FALSE);
    }

    public Boolean isFileGhostReadingEnabled() {
        return Optional.of(Boolean.valueOf(fileReading)).filter(fr -> fr.equals(Boolean.TRUE)).orElse(Boolean.FALSE);
    }

    public Boolean isGloballyEnabled() {
        return Boolean.valueOf(enabled);
    }

    public Boolean isFileTraceEnabled() {
        return Boolean.valueOf(fileEnabled);
    }

}
