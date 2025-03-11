package com.ftt.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SpecimenVO {
    private String identification;
    private String categoryName;
    private String location;
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