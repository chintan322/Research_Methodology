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




/**
 *
 * @author chint
 */
public class RSA {

//    private final static BigInteger one      = new BigInteger("1");
//   private final static SecureRandom random = new SecureRandom();
//
//   private BigInteger privateKey;
//   private BigInteger publicKey;
//   private BigInteger modulus;
//
//   // generate an N-bit (roughly) public and private key
//   RSA(int N) {
//      BigInteger p = BigInteger.probablePrime(N/2, random);
//      BigInteger q = BigInteger.probablePrime(N/2, random);
//      BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));
//
//      modulus    = p.multiply(q);                                  
//      publicKey  = new BigInteger("65537");     // common value in practice = 2^16 + 1
//      privateKey = publicKey.modInverse(phi);
//   }
//
//
//   BigInteger encrypt(BigInteger message) {
//      return message.modPow(publicKey, modulus);
//   }
//
//   BigInteger decrypt(BigInteger encrypted) {
//      return encrypted.modPow(privateKey, modulus);
//   }
//
//   public String toString() {
//      String s = "";
//      s += "public  = " + publicKey  + "\n";
//      s += "private = " + privateKey + "\n";
//      s += "modulus = " + modulus;
//      return s;
//   }
// 
//   public static void main(String[] args) {
//      int N = Integer.parseInt("123");
//      RSA key = new RSA(N);
//      System.out.println(key);
// 
//      // create random message, encrypt and decrypt
//      BigInteger message = new BigInteger(N-1, random);
//
//      //// create message by converting string to integer
//      // String s = "test";
//      // byte[] bytes = s.getBytes();
//      // BigInteger message = new BigInteger(bytes);
//
//      BigInteger encrypt = key.encrypt(message);
//      BigInteger decrypt = key.decrypt(encrypt);
//      System.out.println("message   = " + message);
//      System.out.println("encrypted = " + encrypt);
//      System.out.println("decrypted = " + decrypt);
//   }
    
//    -----------------------------------------------------------------------------
    
    private BigInteger p, q;
    private BigInteger n;
    private BigInteger PhiN;
    private BigInteger e, d;

    public RSA() {
        initialize();
    }

    public void initialize() {
        int SIZE = 512;
        /* Step 1: Select two large prime numbers. Say p and q. */
        p = new BigInteger(SIZE, 15, new Random());
        q = new BigInteger(SIZE, 15, new Random());
        /* Step 2: Calculate n = p.q */
        n = p.multiply(q);
        /* Step 3: Calculate ø(n) = (p - 1).(q - 1) */
        PhiN = p.subtract(BigInteger.valueOf(1));
        PhiN = PhiN.multiply(q.subtract(BigInteger.valueOf(1)));
        /* Step 4: Find e such that gcd(e, ø(n)) = 1 ; 1 < e < ø(n) */
        do {
            e = new BigInteger(2 * SIZE, new Random());
        } while ((e.compareTo(PhiN) != 1)
                || (e.gcd(PhiN).compareTo(BigInteger.valueOf(1)) != 0));
        /* Step 5: Calculate d such that e.d = 1 (mod ø(n)) */
        d = e.modInverse(PhiN);
    }

    public BigInteger encrypt(BigInteger plaintext) {
        return plaintext.modPow(e, n);
    }

    public BigInteger decrypt(BigInteger ciphertext) {
        return ciphertext.modPow(d, n);
    }

    public static void main(String[] args) throws IOException {
        RSA app = new RSA();
        int plaintext;
        System.out.println("Enter any character : ");
        plaintext = System.in.read();
        BigInteger bplaintext, bciphertext;
        bplaintext = BigInteger.valueOf((long) plaintext);
        
        long startTime = System.nanoTime();
        bciphertext = app.encrypt(bplaintext);
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Encryption Time"+totalTime);
        
        System.out.println("Plaintext : " + new String(bplaintext.toByteArray() ));
        System.out.println("Ciphertext : " + new String(bciphertext.toByteArray() ));
        
        startTime = System.nanoTime();
        bplaintext = app.decrypt(bciphertext);
        endTime   = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Decryption Time"+totalTime);
        
        
        System.out.println("After Decryption Plaintext : "
                + new String(bplaintext.toByteArray() ));
    }
        
}

// Reference: https://introcs.cs.princeton.edu/java/99crypto/RSA.java.html
// https://stackoverflow.com/questions/15920739/rsa-implementation-using-java/23795194
