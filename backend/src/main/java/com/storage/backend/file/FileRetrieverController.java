package com.storage.backend.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping(path = "api/v1/files/{fileName}")
public class FileRetrieverController {
    private final FileService fileService;

    @Autowired
    public FileRetrieverController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws IOException {
        return this.fileService.makeFileResponseEntity(fileName);
    }
}
