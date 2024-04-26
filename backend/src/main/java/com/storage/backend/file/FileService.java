package com.storage.backend.file;

import com.storage.discord.DiscordFileBot;
import com.storage.filehandler.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final DiscordFileBot discordBot;
    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
        this.discordBot = new DiscordFileBot();
    }
    public ResponseEntity<List<File>> getFileUploads() {
        return ResponseEntity.status(HttpStatus.OK).body(this.fileRepository.findAll());
    }

    public ResponseEntity<String> handleFileUpload(MultipartFile file) throws IOException {
        List<Long> ids = discordBot.sendFile(file);
        File fileModel = new File(file.getOriginalFilename(), file.getSize(), ids);
        this.fileRepository.save(fileModel);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }
    public byte[] retrieveFile(String fileName) throws IOException {
        File fileModel = this.fileRepository.findByfileName(fileName);
        List<Long> ids = fileModel.getMessage_ids();

        ArrayList<byte[]> byteArr = new ArrayList<>();

        for(int i = 0; i<ids.size(); i++) {
            String name = fileName.split("\\.")[0] + i + "." + fileName.split("\\.")[1];

            java.io.File file = this.discordBot.getFile(name, ids.get(i));

            while(!file.exists()) {}
            byte[] bytes = Files.readAllBytes(file.toPath().toAbsolutePath());
            byteArr.add(bytes);
            file.delete();
        }
        return FileHandler.mergeFile(byteArr, fileModel.getFileSize());
    }
    public ResponseEntity<Resource> makeFileResponseEntity(String fileName) throws IOException {
        byte[] mergedFileBytes = this.retrieveFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(mergedFileBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(mergedFileBytes.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
    public ResponseEntity<String> deleteFile(String fileName) {
        long deleted = this.fileRepository.removeByfileName(fileName);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
