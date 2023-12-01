package com.example.jaj;
import android.os.Build;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
public class AesHelper {

    private static final byte[] DEFAULT_KEY = new byte[32]; // Set your desired default key
    private static final byte[] DEFAULT_IV = new byte[16]; // Set your desired default IV

    private static byte[] generateKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256 bits for AES-256
        secureRandom.nextBytes(keyBytes);
        return keyBytes;
    }

    private static byte[] generateIV() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] ivBytes = new byte[16]; // 128 bits for AES
        secureRandom.nextBytes(ivBytes);
        return ivBytes;
    }

    public static String encrypt(String plainText) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(DEFAULT_KEY, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(DEFAULT_IV);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));

            // Log the IV for debugging
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                System.out.println("IV: " + Base64.getEncoder().encodeToString(DEFAULT_IV));
            }

            // Log the encrypted result for debugging
            String encryptedResult = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                encryptedResult = Base64.getEncoder().encodeToString(encryptedBytes);
            }
            System.out.println("Encrypted Result: " + encryptedResult);

            return encryptedResult;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |
                 UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String cipherText) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(DEFAULT_KEY, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // Extract the IV from the beginning of the ciphertext
            byte[] combined = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                combined = Base64.getDecoder().decode(cipherText);
            }
            byte[] iv = new byte[16];
            System.arraycopy(combined, 0, iv, 0, 16);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

            // Extract the encrypted data from the remaining characters
            byte[] encryptedBytes = new byte[combined.length - 16];
            System.arraycopy(combined, 16, encryptedBytes, 0, encryptedBytes.length);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return new String(decryptedBytes, "UTF-8");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |
                 UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }



    public static void main(String[] args) {
        // Test the encryption and decryption
        String originalText = "Hello, AES!";
        String encryptedText = encrypt(originalText);
        String decryptedText = decrypt(encryptedText);

        System.out.println("Original: " + originalText);
        System.out.println("Encrypted: " + encryptedText);
        System.out.println("Decrypted: " + decryptedText);
    }
}