/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package research_methodology;

import java.util.Base64;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author chint
 */
public class DES {


    
    public static void main(String [] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
    {
        String originalString="ABCDEFGH";
        byte [] msgBytes=originalString.getBytes();        
        byte [] keyBytes  = {(byte)0xFE, (byte)0xDC, (byte)0xBA, (byte)0x98, (byte)0x76, (byte)0x54, (byte)0x32, (byte)0x10};
        SecretKeySpec myDesKey = new SecretKeySpec(keyBytes, "DES");

        //to encrypt a message
        long startTime = System.nanoTime();
        String encryptedString=encryptMsg(msgBytes, myDesKey);
        long endTime   = System.nanoTime();
        long totalTimeEn = endTime - startTime;
        

        //to decrypt a message
        startTime = System.nanoTime();
        String decryptedString = decryptMsg(Base64.getDecoder().decode(encryptedString), myDesKey);
        endTime   = System.nanoTime();
        long totalTimeDe = endTime - startTime;
        

        System.out.println("DES");
        System.out.println("Your PlainText word is :" + originalString);
        System.out.println("Your Encrypted word is :" + encryptedString);
        System.out.println("Your Decrypted word is :" + decryptedString);
        System.out.println("Encryption Time : "+totalTimeEn);
        System.out.println("Decryption Time : "+totalTimeDe);

    } //end main

    //encryption function
    public static String encryptMsg(byte [] msgBytes, SecretKey myDesKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        Cipher desCipher;
        // Create the cipher 
        desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
        byte[] textEncrypted = desCipher.doFinal(msgBytes);

        // converts to base64 for easier display.
        byte[] base64Cipher = Base64.getEncoder().encode(textEncrypted);
        return new String(base64Cipher);
    } //end encryptMsg

    public static String decryptMsg(byte [] cipherBytes, SecretKey myDesKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        Cipher desCipher; 
        desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
        byte[] textDecrypted=desCipher.doFinal(cipherBytes);

        return new String(textDecrypted);
        
    } //end decryptMsg
}

// Reference: https://www.javatpoint.com/java-code-for-des
//https://stackoverflow.com/questions/19534916/des-implementation-in-java-can-not-get-the-plaintext