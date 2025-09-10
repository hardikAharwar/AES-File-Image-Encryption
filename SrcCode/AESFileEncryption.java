package com.sohamglobal.programs;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;

public class AESFileEncryption {
	public static void main(String[] args) throws Exception {
        SecretKey secretKey = generateKey();
        saveKey(secretKey, "secretKey.key");

        String inputFile = "./Files/justinbieber.txt";   
        String encryptedFile = "./EncryptedFiles/enc_justinbieber.txt"; 
        encryptFile(secretKey, inputFile, encryptedFile);

        System.out.println("Encryption successfully completed. Encrypted data saved in EncryptedFiles folder.");
    }

    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, new SecureRandom());
        return keyGen.generateKey();
    }

    public static void saveKey(SecretKey key, String filename) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(key.getEncoded());
        }
    }

   
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
