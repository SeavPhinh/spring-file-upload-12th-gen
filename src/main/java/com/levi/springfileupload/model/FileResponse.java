package com.levi.springfileupload.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class FileResponse {
    private String fileName;
    private String fileDownloadUrl;
    private String fileType;
    private Long size;
}
