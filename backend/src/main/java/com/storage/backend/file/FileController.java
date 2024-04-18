package com.storage.backend.file;

import com.storage.discord.DiscordFileBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "api/v1/files")
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public List<File> getFileUploads() {
        return fileService.getFileUploads();
    }

    @PostMapping
    public String postFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        return fileService.handleFileUpload(file);
    }
}
