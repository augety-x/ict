package com.ftt.controller;

import com.ftt.dto.QueryDTO;
import com.ftt.dto.UploadDTO;
import com.ftt.result.Result;
import com.ftt.service.QueryService;
import com.ftt.service.UploadService;
import com.ftt.vo.SpecimenVO;
import com.ftt.vo.UploadVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private UploadService uploadService;
    @Autowired
    private QueryService queryService;
    @PostMapping(value = "/upload",consumes = "multipart/form-data")
    @ApiOperation("文件上传")
    Result<UploadVO> upload(@RequestParam("file") MultipartFile file,
                            @RequestParam("specimen_identification") String specimenIdentification,
                            @RequestParam("category_name") String categoryName,
                            @RequestParam("user_id") Integer user_id) throws IOException {
        // 将接收到的参数封装到 DTO 中
        UploadDTO fileUploadDTO = new UploadDTO();
        fileUploadDTO.setFile(file);

        fileUploadDTO.setSpecimenIdentification(specimenIdentification);
        fileUploadDTO.setCategoryName(categoryName);
        fileUploadDTO.setUserId(user_id);
        return uploadService.uploadFile(fileUploadDTO);
    }
    @PostMapping(value = "/insertspecimen",consumes = "multipart/form-data")
    @ApiOperation("新增标本")
    Result<UploadVO> insertspecimen(@RequestParam("file") MultipartFile file,
                            @RequestParam("specimen_identification") String specimenIdentification,
                            @RequestParam("category_name") String categoryName,
                            @RequestParam("user_id") Integer user_id) throws IOException {
        // 将接收到的参数封装到 DTO 中
        UploadDTO fileUploadDTO = new UploadDTO();
        fileUploadDTO.setFile(file);
        fileUploadDTO.setSpecimenIdentification(specimenIdentification);
        fileUploadDTO.setCategoryName(categoryName);
        fileUploadDTO.setUserId(user_id);
        return uploadService.insertSpecimen(fileUploadDTO);
    }
    @PostMapping("/queryspecimen")
    public Result<List<SpecimenVO>> querySpecimens(@RequestBody QueryDTO queryDTO) {

            return queryService.querySpecimens(queryDTO);
    }

}
