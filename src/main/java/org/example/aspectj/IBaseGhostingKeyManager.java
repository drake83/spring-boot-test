package org.example.aspectj;

import java.io.Serializable;
import java.util.Optional;

public interface IBaseGhostingKeyManager extends Serializable {
    String SEPARATOR = "|";

    default String createKey(String connectorClass, String input) {
        return this.concatForKey(connectorClass, input);
    }

    default String concatForKey(String connectorClass, String input) {
        return (String) Optional.ofNullable(connectorClass).orElseThrow(() -> {
            return new RuntimeException("A connector name is mandatory");
        }) + "|" + input;
    }
}
