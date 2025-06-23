package com.storage.backend.file;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @JsonView(value = Views.ExternalView.class)
    public ResponseEntity<List<File>> getFileUploads() {
        return fileService.getFileUploads();
    }

    @PostMapping
    public ResponseEntity<String> postFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        return fileService.handleFileUpload(file);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws Exception {
        return this.fileService.makeFileResponseEntity(fileName);
    }
    @DeleteMapping("/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return this.fileService.deleteFile(fileName);
    }
}
