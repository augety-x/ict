package com.ftt.entity;

import lombok.Data;

@Data
public class UploadImage {
    private String filepath; // 上传的文件
    private Integer specimenId; // 样本标识
    private Integer categoryId; // 分类名称
    private Integer userId; // 用户ID
    private String imageType;
}
