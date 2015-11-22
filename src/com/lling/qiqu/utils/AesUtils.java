package com.lling.qiqu.utils;

/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */



import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * Class that implements AES Algorithmns
 */
public class AesUtils {
    final private static Logger logger = LoggerFactory.getLogger(AesUtils.class);
    
    final private static Hex hexCodec = new Hex();
    private final static String DEFAULT_KEY = "waZkAoftB0MIfEuNsh12RA==";
    private static String encryptionKey = DEFAULT_KEY;

    private AesUtils() {
    }

    /**
     * Must be a Base64 128bit key!!!! If in doubt use {@link #generateKey()}.
     *
     * @param key Base64 128bit
     */
    public static void setEnryptionKey(String key) {
        encryptionKey = key;
    }

    public static String getEnryptionKey() {
        return encryptionKey;
    }

    public static String encrypt(long value) {
        return AesUtils.encrypt(getEnryptionKey(), value);
    }

    public static String encrypt(int value) {
        return AesUtils.encrypt(getEnryptionKey(), value);
    }

    public static String encrypt(String message) {
        return AesUtils.encrypt(getEnryptionKey(), message);
    }

    public static String encryptBase64(long value) {
        return AesUtils.encryptBase64(getEnryptionKey(), value);
    }

    public static String encryptBase64(int value) {
        return AesUtils.encryptBase64(getEnryptionKey(), value);
    }

    public static String encryptBase64(String message) {
        return AesUtils.encryptBase64(getEnryptionKey(), message);
    }

    /**
     * Returns an AES 128bit Base64 key
     *
     * @return AES 128bit Base64 key
     */
    public static String generateKey() {
        return generateKey(128);
    }

    /**
     * Returns an AES nbit Base64 key
     *
     * @param keySize Size of the key
     * @return AES 128bit Base64 key
     */
    public static String generateKey(int keySize) {
        String key = "";
        KeyGenerator kgen = null;
        try {
            kgen = KeyGenerator.getInstance("AES");
            kgen.init(keySize);
            SecretKey skey = kgen.generateKey();
            byte[] raw = skey.getEncoded();
            key = new String(Base64.encodeBase64(raw));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return key;
    }

    /**
     * Encrypts a message using a 128bit Bse64 key using AES.
     *
     * @param key     128bit Base64 AES key
     * @param message Message to encrypt
     * @return hex encoded encrypted message
     */
    public static String encrypt(String key, String message) {
        String encrypted = "";
        try {
            // Generate the secret key specs.
            byte[] keyArray = Base64.decodeBase64(key
                    .getBytes());
            SecretKeySpec skeySpec = new SecretKeySpec(keyArray, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypt = cipher.doFinal(message
                    .getBytes());
            encrypted = new String(hexCodec.encode(encrypt));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return encrypted;
    }

    /**
     * Encrypts a message using a 128bit Bse64 key using AES.
     *
     * @param key     128bit Base64 AES key
     * @param message Message to encrypt
     * @return bse64 encoded encrypted message
     */
    public static String encryptBase64(String key, String message) {
        String encrypted = "";
        try {
            // Generate the secret key specs.
            byte[] keyArray = Base64.decodeBase64(key.getBytes());
            SecretKeySpec skeySpec = new SecretKeySpec(keyArray, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypt = cipher.doFinal(message
                    .getBytes());
            encrypted = new String(Base64.encodeBase64(encrypt));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return encrypted;
    }

    public static String encrypt(String key, long value) {
        return encrypt(key, String.valueOf(value));
    }

    public static String encrypt(String key, int value) {
        return encrypt(key, String.valueOf(value));
    }

    public static String encrypt(String key, boolean value) {
        return encrypt(key, String.valueOf(value));
    }

    public static String encrypt(String key, char value) {
        return encrypt(key, String.valueOf(value));
    }

    public static String encrypt(String key, byte value) {
        return encrypt(key, String.valueOf(value));
    }

    public static String encrypt(String key, float value) {
        return encrypt(key, String.valueOf(value));
    }

    public static String encrypt(String key, double value) {
        return encrypt(key, String.valueOf(value));
    }

    public static String encryptBase64(String key, long value) {
        return encryptBase64(key, String.valueOf(value));
    }

    public static String encryptBase64(String key, int value) {
        return encryptBase64(key, String.valueOf(value));
    }

    public static String encryptBase64(String key, boolean value) {
        return encryptBase64(key, String.valueOf(value));
    }

    public static String encryptBase64(String key, char value) {
        return encryptBase64(key, String.valueOf(value));
    }

    public static String encryptBase64(String key, byte value) {
        return encryptBase64(key, String.valueOf(value));
    }

    public static String encryptBase64(String key, float value) {
        return encryptBase64(key, String.valueOf(value));
    }

    public static String encryptBase64(String key, double value) {
        return encryptBase64(key, String.valueOf(value));
    }

    /**
     * Decrypts a AES ecrypted message with a Base64 128bit key
     *
     * @param key   AES Base64 128bit key
     * @param value hex encoded encrypted message to decrypt
     * @return the original message
     */
    public static String decrypt(String key, String value) {
        String decrypted = "";
        try {
            // Get the KeyGenerator
            byte[] keyArray = Base64.decodeBase64(key.getBytes());
            SecretKeySpec skeySpec = new SecretKeySpec(keyArray, "AES");
            // Instantiate the cipher
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);

            // byte[] encrypted =
            // urlCodec.decode(value.getBytes(ICoreFrameworkConstants.DEFAULT_STRING_ENCODING));
            byte[] encrypted = hexCodec.decode(value
                    .getBytes());
            byte[] original = cipher.doFinal(encrypted);
            decrypted = new String(original);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            decrypted = "";
        }
        return decrypted;
    }

    /**
     * Decrypts a AES ecrypted message with a Base64 128bit key
     *
     * @param key   AES Base64 128bit key
     * @param value Base64 encoded encrypted message to decrypt
     * @return the original message
     */
    public static String decryptBase64(String key, String value) {
        String decrypted = "";
        try {
            // Get the KeyGenerator
            byte[] keyArray = Base64.decodeBase64(key
                    .getBytes());
            SecretKeySpec skeySpec = new SecretKeySpec(keyArray, "AES");
            // Instantiate the cipher
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);

            // byte[] encrypted =
            // urlCodec.decode(value.getBytes(ICoreFrameworkConstants.DEFAULT_STRING_ENCODING));
            byte[] encrypted =
                    Base64.decodeBase64(value.getBytes());
            byte[] original = cipher.doFinal(encrypted);
            decrypted = new String(original);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            decrypted = "";
        }
        return decrypted;
    }

    public static String decrypt(String value) {
        String key = getEnryptionKey();
        if (logger.isDebugEnabled()) {
            logger.debug("key: " + key);
            logger.debug("encrypted message: " + value);
        }
        return AesUtils.decrypt(key, value);
    }

    public static String decryptBase64(String value) {
        String key = getEnryptionKey();
        if (logger.isDebugEnabled()) {
            logger.debug("key: " + key);
            logger.debug("encrypted message: " + value);
        }
        return AesUtils.decryptBase64(key, value);
    }
}
