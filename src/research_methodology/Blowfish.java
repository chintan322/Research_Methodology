/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package research_methodology;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;





/**
 *
 * @author chint
 */
public class Blowfish {

    /**
     * @param args the command line arguments
     */
    private static final String key = "123";
    
    public static String encrypt(String password){
        try{
            byte[] keyData = (key).getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyData,"Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] hasil = cipher.doFinal(password.getBytes());
            return new String(Base64.getEncoder().encode(hasil));
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static String decrypt(String string){
        try{
            byte[] keyData = (key).getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyData,"Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] hasil = cipher.doFinal(java.util.Base64.getDecoder().decode(string));
            return new String(hasil);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static void main(String args[]) {
        
        String originalString = "ABCDEFGH";
        long startTime = System.nanoTime();
        String encryptedString = encrypt(originalString);
        long endTime   = System.nanoTime();
        long totalTimeEn = endTime - startTime;
        
        
       
        
        
        
        
        startTime = System.nanoTime();
        String decryptedString = decrypt(encryptedString);
        endTime   = System.nanoTime();
        long totalTimeDe = endTime - startTime;
        
        System.out.println("Blowfish");
        System.out.println("Your PlainText word is :" + originalString);
        System.out.println("Your Encrypted word is :" + encryptedString);
        System.out.println("Your Decrypted word is :" + decryptedString);
        System.out.println("Encryption Time : "+totalTimeEn);
        System.out.println("Decryption Time : "+totalTimeDe);
        
        
        
    }
}

//Reference: https://medium.com/@arie.valiant/java-cryptography-blowfish-encryption-decryption-tutorial-1db5f2cc15f1