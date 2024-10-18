package org.example.aspectj;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface IGhostingHashingManager extends Serializable {
    default String hashString(String input) {
        String DIGEST_TYPE = "SHA-224";

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(DIGEST_TYPE);
        } catch (NoSuchAlgorithmException var6) {
            throw new RuntimeException(var6);
        }

        byte[] result = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        BigInteger number = new BigInteger(1, result);
        return String.format("%064d", number);
    }
}
