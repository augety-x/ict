package com.ftt.mapper;

import com.ftt.entity.Specimen;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface QueryMapper {

    List<Specimen> findByUserIdAndCategoryId(Integer userId, Integer categoryId, Integer offset, Integer limit);
    List<Specimen> findByIdentificationAndUserIdAndReviewStatus(
            @Param("identification") String identification,
            @Param("userId") Integer userId,
            @Param("reviewStatus") Integer reviewStatus,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset
    );}
