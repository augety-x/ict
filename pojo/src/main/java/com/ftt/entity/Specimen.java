package com.ftt.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class Specimen {
    private Integer id;
    private Integer categoryId;
    private String location;
    private String identification;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal altitude;
    private Integer userId;
    private String createdBy;
    private Integer reviewStatus;
    private Integer reviewer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}