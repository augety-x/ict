package com.ftt.service.impl;

import com.ftt.dto.ImageRequestDTO;
import com.ftt.mapper.UploadMapper;
import com.ftt.result.Result;
import com.ftt.service.ImageService;
import com.ftt.vo.ImageResponseVO;
import com.ftt.vo.ImageResponseVOO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    UploadMapper uploadMapper;
    @Override
    public Result<List<ImageResponseVO>> getImages(ImageRequestDTO request) {


        // 获取分类ID和标本ID
        String category = request.getCategory();
        if (category != null) {
            Integer categoryId = uploadMapper.findIdByCategoryName(category);
            request.setCategoryId(categoryId);
        }else
            Result.error("分类名称不存在");

        String specimen = request.getSpecimen_id();
        if (specimen != null) {
            Integer specimenId = uploadMapper.findIdByIdentification(specimen);
            request.setSpecimenId(specimenId);
        }else
            Result.error("标本ID不存在");

//         根据条件查询图像数据

        List<ImageResponseVOO> images = uploadMapper.findImagesByCriteria(request);
        // 按 image_id 分组
        Map<Integer, List<ImageResponseVOO>> imageMap = images.stream()
                .collect(Collectors.groupingBy(ImageResponseVOO::getImage_id));

        // 处理每个 image_id 对应的图片
        List<ImageResponseVOO> result = new ArrayList<>();
        List<ImageResponseVO> ans = new ArrayList<>();

        for (Map.Entry<Integer, List<ImageResponseVOO>> entry : imageMap.entrySet()) {
            List<ImageResponseVOO> imageList = entry.getValue();

            // 找到原图和缩略图
            ImageResponseVOO originalImage = null;
            ImageResponseVOO thumbnailImage = null;

            for (ImageResponseVOO image : imageList) {
                if ("thumb".equals(image.getFile_type())) {
                    thumbnailImage = image;
                } else  {
                    originalImage = image;
                }
            }

            // 将缩略图的 file_path 赋值给原图的 thumbnail_path
            if (originalImage != null && thumbnailImage != null) {
                originalImage.setThumbnail_path(thumbnailImage.getImage_path());
            }

            // 只保留原图
            if (originalImage != null) {
                result.add(originalImage);
            }

            for (ImageResponseVOO image : imageList)
            {
                ImageResponseVO myimage = new ImageResponseVO();
                BeanUtils.copyProperties(image,myimage);
                ans.add(myimage);
            }
        }
        return Result.success(ans);
    }
//        // 把所有数据从DTO中拿出来
//        String user_id = request.getUser_id();
//        String category = request.getCategory();
//        Integer categoryId = uploadMapper.findIdByCategoryName(category);
//        String specimen= request.getSpecimen_id();
//        Integer specimenId = uploadMapper.findIdByIdentification(specimen);
//        LocalDateTime start_datetime = request.getStart_datetime();
//        LocalDateTime end_datetime = request.getEnd_datetime();
//
//
//        List<ImageResponseVO> images = new ArrayList<>();
//        ImageResponseVO image = new ImageResponseVO();
//        image.setImage_id(1);
//        image.setFile_id(101);
//        image.setImage_path("/path/to/image.jpg");
//        image.setThumbnail_path("/path/to/thumbnail.jpg");
//        image.setSpecimen_identification(123);
//        image.setCategory_name("Wildlife");
//        image.setHeight(1080);
//        image.setWidth(1920);
//        image.setLatitude(34.0522);
//        image.setLongitude(-118.2437);
//        image.setAltitude(89.0);
//        image.setTaken_at(java.time.LocalDateTime.now());
//        image.setExtra_data(new HashMap<>());
//        images.add(image);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("status", 200);
//        response.put("message", "Success");
//        response.put("data", images);
//        return Result.success(images);
    }

