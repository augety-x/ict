package com.ftt.dto;

import lombok.Data;

@Data
public class QueryDTO {
    private String identification;
    private String categoryName;
    private Integer userId;
    private Integer reviewStatus;
    private Integer page;
    private Integer limit;}