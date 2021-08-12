/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package research_methodology;

import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.security.InvalidAlgorithmParameterException;  
import java.security.InvalidKeyException;  
import java.security.NoSuchAlgorithmException;  
import java.security.spec.AlgorithmParameterSpec;  
import javax.crypto.Cipher;  
import javax.crypto.CipherInputStream;  
import javax.crypto.CipherOutputStream;  
import javax.crypto.KeyGenerator;  
import javax.crypto.NoSuchPaddingException;  
import javax.crypto.SecretKey;  
import javax.crypto.spec.IvParameterSpec; 

/**
 *
 * @author chint
 */
public class DES {

    /**
     * @param args the command line arguments
     */
    //creating an instance of the Cipher class for encryption  
    private static Cipher encrypt;  
    //creating an instance of the Cipher class for decryption  
    private static Cipher decrypt;  
    //initializing vector  
    private static final byte[] initialization_vector = { 22, 33, 11, 44, 55, 99, 66, 77 };  
    
    public static void main(String args[]) {
        // TODO code application logic here
//        System.out.println("Hello world");
        //path of the file that we want to encrypt  
        String textFile = "C:\\Users\\chint\\OneDrive\\Documents\\NetBeansProjects\\Research_Methodology\\src\\research_methodology\\abc.txt";  
        //path of the encrypted file that we get as output  
        String encryptedData = "C:\\Users\\chint\\OneDrive\\Documents\\NetBeansProjects\\Research_Methodology\\src\\research_methodology\\encrypteddata.txt";  
        //path of the decrypted file that we get as output  
        String decryptedData = "C:\\Users\\chint\\OneDrive\\Documents\\NetBeansProjects\\Research_Methodology\\src\\research_methodology\\decrypteddata.txt";  
        try   
        {  
            //generating keys by using the KeyGenerator class  
            SecretKey scrtkey = KeyGenerator.getInstance("DES").generateKey();  
            AlgorithmParameterSpec aps = new IvParameterSpec(initialization_vector);  
            //setting encryption mode  
            encrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");  
            encrypt.init(Cipher.ENCRYPT_MODE, scrtkey, aps);  
            //setting decryption mode  
            decrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");  
            decrypt.init(Cipher.DECRYPT_MODE, scrtkey, aps);  
            
            
            
            //calling encrypt() method to encrypt the file  
            
            long startTime = System.nanoTime();
            encryption(new FileInputStream(textFile), new FileOutputStream(encryptedData));  
            long endTime   = System.nanoTime();
            long totalTime = endTime - startTime;
            System.out.println("Encryption Time"+totalTime);
            
            
            
            //calling decrypt() method to decrypt the file  
            
            startTime = System.nanoTime();
            decryption(new FileInputStream(encryptedData), new FileOutputStream(decryptedData)); 
            endTime   = System.nanoTime();
            totalTime = endTime - startTime;
            System.out.println("Decryption Time"+totalTime);
            
            
            
            
            
            
            
            
            
            //prints the stetment if the program runs successfully  
            System.out.println("The encrypted and decrypted files have been created successfully.");  
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IOException e)   
        {  
        //prints the message (if any) related to exceptions  
            e.printStackTrace();  
        } 
    }
    private static void encryption(InputStream input, OutputStream output) throws IOException{  
        output = new CipherOutputStream(output, encrypt);  
        //calling the writeBytes() method to write the encrypted bytes to the file   
        writeBytes(input, output);  
    }   
    //method for decryption  
    private static void decryption(InputStream input, OutputStream output)  
    throws IOException   
    {  
        input = new CipherInputStream(input, decrypt);  
        //calling the writeBytes() method to write the decrypted bytes to the file    
        writeBytes(input, output);  
    }  
    //method for writting bytes to the files   
    private static void writeBytes(InputStream input, OutputStream output)  
    throws IOException   
    {  
        byte[] writeBuffer = new byte[512];  
        int readBytes = 0;  
        while ((readBytes = input.read(writeBuffer)) >= 0)   
        {  
        output.write(writeBuffer, 0, readBytes);  
        }  
        //closing the output stream  
        output.close();  
        //closing the input stream  
        input.close();  
    }   
}

// Reference: https://www.javatpoint.com/java-code-for-des