package ru.lovkost.services;

import com.vaadin.flow.component.notification.NotificationVariant;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class PasswordService {

    public static String generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey key = keyGenerator.generateKey();
        Base64.Encoder encoder = Base64.getEncoder();
        var s = encoder.encodeToString(key.getEncoded()) + "~~" + generateIv();
        return s;
    }

    public static String encrypt(String input, String keyIV
                                 ) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        var keys = keyIV.split("~~");
        var iv = new IvParameterSpec(Base64.getDecoder().decode(keys[1]));
        var key = convertStringToSecretKeyto(keys[0]);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }
    public static String decrypt( String cipherText, String keyIV) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var keys = keyIV.split("~~");
        var key = convertStringToSecretKeyto(keys[0]);
        var iv =  new IvParameterSpec(Base64.getDecoder().decode(keys[1]));
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        try {
            byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
            return new String(plainText);

        }catch (BadPaddingException | IllegalBlockSizeException e) {
            return cipherText;
        }
    }

    public static String generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        Base64.Encoder encoder = Base64.getEncoder();
        var s = encoder.encodeToString(iv);
        return  s;
    }
    public static SecretKey convertStringToSecretKeyto(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        return originalKey;
    }
}
