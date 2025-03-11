package com.ftt.vo;

import lombok.Data;

@Data
public class UploadVO {
    private Integer imageId; // 图像ID
    private Integer fileId; // 文件ID
    private String imagePath; // 图像路径
    private String thumbPath; // 缩略图路径
    private String specimenIdentification; // 样本标识
    private String categoryName; // 分类名称
}