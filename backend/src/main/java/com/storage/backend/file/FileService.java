package com.storage.backend.file;

import com.storage.discord.DiscordFileBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private DiscordFileBot discordBot;
    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
        this.discordBot = new DiscordFileBot();
    }
    public List<File> getFileUploads() {
        return fileRepository.findAll();
    }

    public String handleFileUpload(MultipartFile file) throws IOException {
        File fileModel = discordBot.sendFile(file);
        this.fileRepository.save(fileModel);
        return "Success";
    }
}
