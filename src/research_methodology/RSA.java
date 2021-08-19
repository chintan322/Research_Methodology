/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package research_methodology;


import java.math.BigInteger;
import java.security.SecureRandom;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;  



/**
 *
 * @author chint
 */
public class RSA {

    private final static BigInteger one      = new BigInteger("1");
   private final static SecureRandom random = new SecureRandom();

   private BigInteger privateKey;
   private BigInteger publicKey;
   private BigInteger modulus;

   // generate an N-bit (roughly) public and private key
   RSA(int N) {
      BigInteger p = BigInteger.probablePrime(N/2, random);
      BigInteger q = BigInteger.probablePrime(N/2, random);
      BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

      modulus    = p.multiply(q);                                  
      publicKey  = new BigInteger("65537");     // common value in practice = 2^16 + 1
      privateKey = publicKey.modInverse(phi);
   }


   BigInteger encrypt(BigInteger message) {
      return message.modPow(publicKey, modulus);
   }

   BigInteger decrypt(BigInteger encrypted) {
      return encrypted.modPow(privateKey, modulus);
   }
   
   public String bytesToString(byte[] b) {
        byte[] b2 = new byte[b.length + 1];
        b2[0] = 1;
        System.arraycopy(b, 0, b2, 1, b.length);
        return new BigInteger(b2).toString(36);
    }

   public String toString() {
      String s = "";
      s += "public  = " + publicKey  + "\n";
      s += "private = " + privateKey + "\n";
      s += "modulus = " + modulus;
      return s;
   }
 
   public static void main(String[] args) {
      int N = Integer.parseInt("123");
      RSA key = new RSA(N);
      System.out.println(key);
 
      // create random message, encrypt and decrypt
      // BigInteger message = new BigInteger(N-1, random);

      // create message by converting string to integer
       String originalString = "ABCDEFGH";
       byte[] bytes = originalString.getBytes();
       BigInteger message = new BigInteger(bytes);

        long startTime = System.nanoTime();
        BigInteger encryptedString = key.encrypt(message);
        long endTime   = System.nanoTime();
        long totalTimeEn = endTime - startTime;

        startTime = System.nanoTime();
        BigInteger decryptedString = key.decrypt(encryptedString);
        endTime   = System.nanoTime();
        long totalTimeDe = endTime - startTime;
        
        
//        byte byteArray[] = decryptedString.toByteArray();  
//        System.out.println(Arrays.toString(byteArray)); 
      
      
        System.out.println("RSA");
        System.out.println("Your Original PlainText word is :" + originalString);
        System.out.println("Your Modified PlainText(Bytes Converted) word is :" + message);
        System.out.println("Your Encrypted word is :" + encryptedString);
        System.out.println("Your Decrypted word is :" + decryptedString);
        System.out.println("Encryption Time : "+totalTimeEn);
        System.out.println("Decryption Time : "+totalTimeDe);
   }
           
}

// Reference: https://introcs.cs.princeton.edu/java/99crypto/RSA.java.html
// https://stackoverflow.com/questions/15920739/rsa-implementation-using-java/23795194
