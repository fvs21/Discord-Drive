package com.storage.filehandler;

import net.dv8tion.jda.api.utils.FileUpload;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FileHandler {
    private static final int fileSize = 25*1048576;
    public static ArrayList<FileUpload> splitFile(MultipartFile file, String fileName) throws IOException {
        byte[] byteArr = file.getBytes();
        ArrayList<byte[]> splittedBytes = new ArrayList<>();
        ArrayList<FileUpload> splittedFiles = new ArrayList<>();
        if(byteArr.length / 1e6 > 25) {
            int numberOfChunks = (int) (byteArr.length / 1e6) / 25; //because file size limit is 25mb (rounded down)

            for(int i = 0; i<numberOfChunks; i++)  {
                byte[] subArr = Arrays.copyOfRange(byteArr, i*fileSize, (i+1)*(fileSize));
                splittedBytes.add(subArr);
            }

            splittedBytes.add(Arrays.copyOfRange(byteArr, numberOfChunks*fileSize, byteArr.length));
        }
        else {
            splittedBytes.add(byteArr);
        }
        int nameCounter = 0;
        for(byte[] element : splittedBytes) {
            String splittedFileName = fileName.split("\\.")[0] + nameCounter + "." + fileName.split("\\.")[1];
            splittedFiles.add(makeFile(element, splittedFileName));
            nameCounter++;
        }
        return splittedFiles;
    }
    private static FileUpload makeFile(byte[] bytes, String fileName) {
        FileUpload file = FileUpload.fromData(bytes, fileName);
        return file;
    }
    public static byte[] mergeFile(ArrayList<byte[]> splittedBytes, long fileSize) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for(byte[] bytes : splittedBytes) {
            outputStream.write(bytes);
        }
        return outputStream.toByteArray();
    }
}
