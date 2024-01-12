package com.levi.springfileupload.controller;

import com.levi.springfileupload.model.ApiResponse;
import com.levi.springfileupload.model.FileResponse;
import com.levi.springfileupload.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) throws IOException {
        String fileName = fileService.saveFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile").queryParam("fileName", fileName).toUriString();
        ApiResponse<FileResponse> response = ApiResponse.<FileResponse>builder()
                .message("Successfully upload file").status(HttpStatus.OK).code(201)
                .payload(new FileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize())).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) throws IOException {
        Resource resource = fileService.getFileByFileName(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(resource.getInputStream()));
    }

    @GetMapping("/files/name")
    public ResponseEntity<Resource> getFile(@RequestParam String fileName) throws IOException {
        Resource resource = fileService.getFileByFileName(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(resource.getInputStream()));
    }
}
