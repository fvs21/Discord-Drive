package com.storage.backend.file;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatusCode;
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
    public List<File> getFileUploads() {
        return fileService.getFileUploads();
    }

    @PostMapping
    public ResponseEntity<File> postFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.status(201).body(fileService.handleFileUpload(file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws Exception {
        return this.fileService.makeFileResponseEntity(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        return this.fileService.deleteFile(id);
    }
}
