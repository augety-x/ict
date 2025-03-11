package com.ftt.controller;

import com.ftt.dto.ImageRequestDTO;
import com.ftt.result.Result;
import com.ftt.service.ImageService;
import com.ftt.vo.ImageResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/image")
public class ImageController {
    @Autowired
    ImageService imageService;
    @PostMapping("/getimages")
    public Result<List<ImageResponseVO>> getImages(@RequestBody ImageRequestDTO request) {
        // 将 String 转换为 LocalDateTime
        // 将 String 转换为 LocalDateTime
        if (request.getStart_datetime() != null) {
            // 先解析为 LocalDate
            LocalDate startDate = LocalDate.parse(request.getStart_datetime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            // 转换为 LocalDateTime，时间部分设置为 00:00:00
            LocalDateTime startDatetime = startDate.atStartOfDay();
            request.setStart_datetimeT(startDatetime);
        }
        if (request.getEnd_datetime() != null) {
            // 先解析为 LocalDate
            LocalDate endDate = LocalDate.parse(request.getEnd_datetime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            // 转换为 LocalDateTime，时间部分设置为 23:59:59
            LocalDateTime endDatetime = endDate.atTime(23, 59, 59);
            request.setEnd_datetimeT(endDatetime);
        }

        return imageService.getImages(request);

    }

}
