package com.ftt.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class File {
    private Integer fileId;
    private Integer userId;
    private String fileName;
    private Long fileSize;
    private String fileType;
    private String filePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
