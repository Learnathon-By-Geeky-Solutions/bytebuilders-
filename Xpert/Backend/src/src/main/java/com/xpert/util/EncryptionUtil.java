package com.xpert.util;

import com.xpert.exception.EncryptionException;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for AES-GCM encryption and decryption.
 * This class is non-instantiable and thread-safe.
 */
public class EncryptionUtil {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int IV_SIZE = 12;
    private static final int TAG_LENGTH_BIT = 128;

    private static final SecretKeySpec keySpec;
    private static final SecureRandom secureRandom = new SecureRandom();

    static {
        String secretKey = System.getProperty("encryption.secret");
        if (secretKey == null) {
            secretKey = System.getenv("ENCRYPTION_SECRET"); // fallback to env var
        }
        if (secretKey == null) {
            throw new IllegalStateException("Encryption secret key not provided. Set -Dencryption.secret or ENCRYPTION_SECRET");
        }
        keySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private EncryptionUtil() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    /**
     * Encrypts the given plain text using AES-GCM.
     *
     * @param value The plain text to encrypt
     * @return Base64 encoded encrypted text
     */
    public static String encrypt(String value) {
        try {
            byte[] iv = new byte[IV_SIZE];
            secureRandom.nextBytes(iv);

            GCMParameterSpec ivSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            byte[] encryptedWithIv = new byte[IV_SIZE + encrypted.length];
            System.arraycopy(iv, 0, encryptedWithIv, 0, IV_SIZE);
            System.arraycopy(encrypted, 0, encryptedWithIv, IV_SIZE, encrypted.length);

            return Base64.getEncoder().encodeToString(encryptedWithIv);
        } catch (Exception ex) {
            throw new EncryptionException("Encryption failed", ex);
        }
    }

    /**
     * Decrypts a Base64 encoded encrypted value using AES-GCM.
     *
     * @param encryptedValue The Base64 encoded encrypted text
     * @return The decrypted plain text
     */
    public static String decrypt(String encryptedValue) {
        try {
            byte[] decoded = Base64.getDecoder().decode(encryptedValue);
            byte[] iv = new byte[IV_SIZE];
            System.arraycopy(decoded, 0, iv, 0, IV_SIZE);

            GCMParameterSpec ivSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            byte[] decrypted = cipher.doFinal(decoded, IV_SIZE, decoded.length - IV_SIZE);
            return new String(decrypted);
        } catch (Exception ex) {
            throw new EncryptionException("Decryption failed", ex);
        }
    }
}
