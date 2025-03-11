package com.ftt.service.impl;

import com.ftt.dto.QueryDTO;
import com.ftt.entity.Specimen;
import com.ftt.mapper.QueryMapper;
import com.ftt.mapper.UploadMapper;
import com.ftt.result.Result;
import com.ftt.vo.SpecimenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueryServiceImpl implements com.ftt.service.QueryService{
    @Autowired
    private QueryMapper queryMapper;
    @Autowired
    private UploadMapper uploadMapper;

    @Transactional
    public Result<List<SpecimenVO>> querySpecimens(QueryDTO queryDTO) {
        String categoryName = queryDTO.getCategoryName();
        String identification = queryDTO.getIdentification();
        Integer userId = queryDTO.getUserId();
        Integer reviewStatus = queryDTO.getReviewStatus();
        Integer page = queryDTO.getPage();
        Integer limit = queryDTO.getLimit();

        // 根据 categoryName 查询 rock_category 表，获取 categoryId
        Integer categoryId = uploadMapper.findIdByCategoryName(categoryName);
        if (categoryId == null) {
            return Result.error("Category not found");
        }

        List<Specimen> specimens;
        if (identification == null) {
            // 如果没有传递 identification，则根据 userId 和 categoryId 查询标本
            // 计算 offset
            Integer offset = (page - 1) * limit;
            specimens = queryMapper.findByUserIdAndCategoryId(userId, categoryId, offset, limit);
        } else {
            // 如果传递了 identification，则根据 identification、userId 和 reviewStatus 查询标本
            Integer specimenId = uploadMapper.findIdByIdentification(identification);
            if (specimenId == null) {
                return Result.error("Specimen not found");
            }
            Integer offset = (page - 1) * limit;
            specimens = queryMapper.findByIdentificationAndUserIdAndReviewStatus(identification, userId, reviewStatus, limit, offset);
        }

        // 将查询到的标本数据转换为 VO
        List<SpecimenVO> specimenVOs = new ArrayList<>();
        for (Specimen specimen : specimens) {
            SpecimenVO vo = new SpecimenVO();
            vo.setIdentification(uploadMapper.selectSpecimenNameById(specimen.getId()));
            vo.setCategoryName(uploadMapper.selectCategoryNameById(categoryId));
            vo.setLocation(specimen.getLocation());
            vo.setLatitude(specimen.getLatitude());
            vo.setLongitude(specimen.getLongitude());
            vo.setAltitude(specimen.getAltitude());
            vo.setUserId(specimen.getUserId());
            vo.setCreatedBy(specimen.getCreatedBy());
            vo.setReviewStatus(specimen.getReviewStatus());
            vo.setReviewer(specimen.getReviewer());
            vo.setCreatedAt(specimen.getCreatedAt());
            vo.setUpdatedAt(specimen.getUpdatedAt());
            specimenVOs.add(vo);
        }
        return Result.success(specimenVOs);
    }
}
