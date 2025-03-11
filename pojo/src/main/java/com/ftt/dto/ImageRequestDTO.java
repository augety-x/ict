package com.ftt.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImageRequestDTO {
    private String user_id;
    private String category;
    private String specimen_id;
    private String start_datetime;
    private String end_datetime;
    private LocalDateTime start_datetimeT;
    private LocalDateTime end_datetimeT;
    private Integer categoryId; // 新增字段
    private Integer specimenId; // 新增字段


}