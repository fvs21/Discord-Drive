package com.storage.filehandler;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.utils.FileUpload;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class FileHandler {
    //maximum discord's individual file size limit
    private static final int fileSize = 25*1048576; //25 mb
    public static ArrayList<FileUpload> splitFile(MultipartFile file, String fileName) throws Exception {
        //get the bytes from the original file and encrypt them
        byte[] byteArr = encryptBytes(file.getBytes());
        ArrayList<byte[]> splittedBytes = new ArrayList<>(); //arraylist to store the 25 mb file chunks bytes

        // arraylist to store the 25 mb file chunks to send to the discord server. Stores FileUpload object which
        // is compatible with discord's api
        ArrayList<FileUpload> splittedFiles = new ArrayList<>();

        //check if file size is greater than 25 mb
        if(byteArr.length / 1048576 > 25) {
            int numberOfChunks = (int) (byteArr.length / 1e6) / 25; // rounded down
            for(int i = 0; i<numberOfChunks; i++)  {
                //storing 25 mb chunks of the file in a sub array and adding it to the splittedBytes arraylist
                byte[] subArr = Arrays.copyOfRange(byteArr, i*fileSize, (i+1)*(fileSize));
                splittedBytes.add(subArr);
            }

            splittedBytes.add(
                    Arrays.copyOfRange(byteArr, numberOfChunks*fileSize, byteArr.length)
            );
        }
        else {
            //if the file size is less than 25 mb, it is just added to the splittedBytes arraylist
            splittedBytes.add(byteArr);
        }
        int nameCounter = 0;
        for(byte[] element : splittedBytes) {
            //creates a fileUpload object with the original filename plus a number. ex: file0.mp4, file1.mp4
            String splittedFileName = fileName.split("\\.")[0] + nameCounter + "." + fileName.split("\\.")[1];
            splittedFiles.add(makeFile(element, splittedFileName));
            nameCounter++;
        }
        return splittedFiles;
    }
    private static FileUpload makeFile(byte[] bytes, String fileName) {
        //just creates a fileupload object from bytes and returns it
        FileUpload file = FileUpload.fromData(bytes, fileName);
        return file;
    }
    public static byte[] mergeFile(ArrayList<byte[]> splittedBytes) throws Exception {
        //merges the bytes of an arraylist of bytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for(byte[] bytes : splittedBytes) {
            outputStream.write(bytes);
        }
        //returns the bytes decrypted
        return decryptBytes(outputStream.toByteArray());
    }

    private static byte[] encryptBytes(byte[] fileBytes) throws Exception {
        //loads the secret encryption key
        String secretKey = Dotenv.load().get("secret_key");

        SecretKey key = new SecretKeySpec(secretKey.getBytes(), "AES");

        //create cipher object used to encrypt the bytes
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        //returns the bytes encrypted
        return cipher.doFinal(fileBytes);
    }

    private static byte[] decryptBytes(byte[] encryptedBytes) throws Exception {
        //loads the secret encryption key
        String secretKey = Dotenv.load().get("secret_key");

        SecretKey key = new SecretKeySpec(secretKey.getBytes(), "AES");

        //create cipher object used to decrypt the bytes
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        //returns the bytes decrypted
        return cipher.doFinal(encryptedBytes);
    }
}
