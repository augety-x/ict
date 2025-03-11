package com.ftt.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadDTO {
    private MultipartFile file; // 上传的文件
    private String specimenIdentification; // 样本标识
    private String categoryName; // 分类名称
    private Integer userId; // 用户ID
}
