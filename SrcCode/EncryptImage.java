package com.sohamglobal.programs;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptImage {
	public static void main(String[] args) throws Exception {
        // Step 1: Generate AES Key
        SecretKey secretKey = generateKey();

        // Save key to a file for later use in decryption
        saveKey(secretKey, "secretKey.key");

        // Step 2: Encrypt File
        String inputFile = "./Files/nasa.jpg";   // Input file with plain text
        String encryptedFile = "./EncryptedFiles/enc_nasa.jpg"; // Output file to save encrypted text
        encryptFile(secretKey, inputFile, encryptedFile);

        System.out.println("Encryption successfully completed. Encrypted data saved in EncryptedFiles folder.");
    }

    // Make this method public so it can be used in decryption
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, new SecureRandom()); // 256-bit AES key
        return keyGen.generateKey();
    }

    // Save the secret key to a file
    public static void saveKey(SecretKey key, String filename) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(key.getEncoded());
        }
    }

    // Encrypt the file
    public static void encryptFile(SecretKey key, String inputFile, String outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        
        byte[] fileContent = readFile(inputFile);
        byte[] encryptedData = cipher.doFinal(fileContent);
        String encodedData = Base64.getEncoder().encodeToString(encryptedData);

        writeFile(outputFile, encodedData.getBytes());
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
