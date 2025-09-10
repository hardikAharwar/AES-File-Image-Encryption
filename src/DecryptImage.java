package com.sohamglobal.programs;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;

public class DecryptImage {

    public static void main(String[] args) throws Exception {
        // Load the AES key from the saved file
        SecretKey secretKey = loadKey("secretKey.key");

        // Step 1: Decrypt File
        String encryptedFile = "./EncryptedFiles/enc_nasa.jpg"; // File with encrypted data
        String decryptedFile = "./DecryptedFiles/dec_nasa.jpg"; // Output file to store decrypted text
        decryptFile(secretKey, encryptedFile, decryptedFile);

        System.out.println("Decryption completed. Decrypted data saved in DecryptedFiles.");
    }

    // Load the secret key from the file
    public static SecretKey loadKey(String filename) throws IOException {
        byte[] keyBytes = readFile(filename);
        return new SecretKeySpec(keyBytes, "AES");
    }

    // Decrypt the file
    public static void decryptFile(SecretKey key, String inputFile, String outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] encryptedData = readFile(inputFile);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);

        writeFile(outputFile, decryptedData);
    }

    private static byte[] readFile(String filename) throws IOException {
        return new FileInputStream(filename).readAllBytes();
    }

    private static void writeFile(String filename, byte[] data) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(data);
        }
    }
}
