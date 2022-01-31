package com.mangkyu.employment.interview.app.file.controller;

import com.mangkyu.employment.interview.app.file.constants.FileConstants;
import com.mangkyu.employment.interview.app.file.dto.FileUploadResponse;
import com.mangkyu.employment.interview.app.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.mangkyu.employment.interview.app.file.constants.FileConstants.*;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(FILE_API_PREFIX)
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("upload") MultipartFile file) {
        return ResponseEntity.ok(fileService.upload(file));
    }

    @GetMapping(FILE_API_PREFIX + "/{resourceId}")
    public ResponseEntity<Resource> getFileApi(@PathVariable String resourceId) {
        return ResponseEntity.ok(fileService.getFileAsResource(resourceId));
    }

    @GetMapping("/file/{resourceId}")
    public ResponseEntity<Resource> getFile(@PathVariable String resourceId) {
        return ResponseEntity.ok(fileService.getFileAsResource(resourceId));
    }

}