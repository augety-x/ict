package com.ftt.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RockImage {
    private Integer id; // 主键 ID
    private Integer imageId; // 图片表 ID
    private Integer userId; // 用户 ID
    private Integer categoryId; // 分类 ID
    private Integer specimenId; // 样本 ID
    private Integer fileId; // 文件表 ID
    private LocalDateTime createdAt; // 创建时间
    private LocalDateTime updatedAt; // 更新时间
}