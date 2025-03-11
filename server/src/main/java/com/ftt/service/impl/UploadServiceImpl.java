package com.ftt.service.impl;

import com.ftt.constant.FileTypeConstant;
import com.ftt.dto.UploadDTO;
import com.ftt.entity.*;
import com.ftt.mapper.UploadMapper;
import com.ftt.result.Result;
import com.ftt.service.UploadService;
import com.ftt.utils.GetThumbUtil;
import com.ftt.utils.HuaweiObsUtil;
import com.ftt.vo.UploadVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    @Autowired
    private HuaweiObsUtil huaweiObsUtil;
    @Autowired
    private UploadMapper uploadMapper;
    @Autowired
    private GetThumbUtil getThumbUtil;

    @Transactional
    public Result<UploadVO> insertSpecimen(UploadDTO uploadDTO) throws IOException {
        Integer categoryID =uploadMapper.findIdByCategoryName(uploadDTO.getCategoryName()) ;
        if (categoryID  == null) {
            return Result.error(uploadDTO.getCategoryName()+"分类不存在");
        }
        //新增标本
        Specimen specimen = new Specimen();
        BeanUtils.copyProperties(uploadDTO, specimen);
        specimen.setIdentification(uploadDTO.getSpecimenIdentification());
        specimen.setCreatedBy(String.valueOf(uploadDTO.getUserId()));
        specimen.setReviewer((uploadDTO.getUserId()));
        specimen.setReviewStatus(1);
        specimen.setCategoryId(categoryID);
        uploadMapper.insertSpecimen(specimen);
        log.info("新增标本成功");
        return uploadFile(uploadDTO);
    }
    @Transactional
    public Result<UploadVO> uploadFile(UploadDTO uploadDTO) throws IOException {
        MultipartFile file = uploadDTO.getFile();
        String originalFilename = file.getOriginalFilename();
        //TODO 判断要添加到的categoryName和specimenIdentification是否存在，若不存在就返回对应不存在的错误
        if (uploadMapper.findIdByCategoryName(uploadDTO.getCategoryName()) == null) {
            return Result.error("分类不存在");
        }
        if (uploadMapper.findIdByIdentification(uploadDTO.getSpecimenIdentification()) == null) {
            return Result.error(uploadDTO.getSpecimenIdentification()+"标本不存在");
        }


        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String objName = UUID.randomUUID().toString() + suffix;
        //
        String thumbName = "thumb_" + objName;
        UploadImage uploadImage = new UploadImage();
        UploadImage uploadthumb = new UploadImage();
        //TODO 缩略图大小
        byte[] Thumbfile = getThumbUtil.generateThumbnail(file, 80, 80);
        try {
            String filePath = huaweiObsUtil.upload(file.getBytes(), objName);
            String thumbFilePath = huaweiObsUtil.upload(Thumbfile, thumbName);
            uploadImage.setImageType(FileTypeConstant.IMAGE_TYPE[0]);
            uploadImage.setFilepath(filePath);
            uploadImage.setCategoryId(uploadMapper.findIdByCategoryName(uploadDTO.getCategoryName()));
            uploadImage.setUserId(uploadDTO.getUserId());
            uploadImage.setSpecimenId(uploadMapper.findIdByIdentification(uploadDTO.getSpecimenIdentification()));
            uploadthumb.setCategoryId(uploadMapper.findIdByCategoryName(uploadDTO.getCategoryName()));
            uploadthumb.setImageType(FileTypeConstant.IMAGE_TYPE[3]);
            uploadthumb.setFilepath(thumbFilePath);
            uploadthumb.setSpecimenId(uploadMapper.findIdByIdentification(uploadDTO.getSpecimenIdentification()));
            uploadthumb.setUserId(uploadDTO.getUserId());
            RockImage image = getId(uploadImage,-1);
            RockImage thumb = getId(uploadthumb,image.getImageId());
            int file_id = image.getFileId();
            int thumb_id = thumb.getFileId();
            String imagePath = uploadMapper.selectImagePathById(file_id);
            String thumbPath = uploadMapper.selectImagePathById(thumb_id);
            UploadVO uploadVO = new UploadVO();
            BeanUtils.copyProperties(image, uploadVO);
            uploadVO.setThumbPath(thumbPath);
            uploadVO.setImagePath(imagePath);
            uploadVO.setCategoryName(uploadMapper.selectCategoryNameById(image.getCategoryId()));
            uploadVO.setSpecimenIdentification(uploadMapper.selectSpecimenNameById(image.getSpecimenId()));
            log.info("文件上传成功");
            return Result.success(uploadVO);
        } catch (IOException e) {
            log.error("文件上传失败", e);
            return Result.error("文件上传失败");
        }
    }
    @Transactional
    public  RockImage getId(UploadImage uploadImage,int image_id)//type = 0代表原图 1代表缩略图
    {
        // 解析 UploadImage 对象
        int userId = uploadImage.getUserId(); // 将字符串类型的 userId 转换为 int
        String filepath = uploadImage.getFilepath();
        String imageType = uploadImage.getImageType();
        int categoryId = uploadImage.getCategoryId(); // 根据分类名称获取分类 ID
        int specimenId = uploadImage.getSpecimenId(); // 根据样本标识获取样本 ID

        // 插入文件表
        File file = new File();
        file.setUserId(userId);
        file.setFileName(filepath);
        file.setFileSize(0L); // 假设文件大小为 0
        file.setFileType(imageType);
        file.setFilePath(filepath);

        uploadMapper.insertFile(file);
        int fileId = file.getFileId(); // 获取插入后生成的 fileId
        int imageId = 0;
        if(image_id!=-1)
        {
            imageId = image_id;
        }else
        {
            // 插入图片表
            Image image = new Image();
            image.setFileId(fileId);
            image.setUserId(userId);
            image.setHeight(0); // 假设高度为 0
            image.setWidth(0); // 假设宽度为 0
            uploadMapper.insertImage(image);
             imageId= image.getImageId(); // 获取插入后生成的 imageId

        }

        // 插入岩石照片表
        RockImage rockImage = new RockImage();
        rockImage.setImageId(imageId);
        rockImage.setUserId(userId);
        rockImage.setCategoryId(categoryId);
        rockImage.setSpecimenId(specimenId);
        rockImage.setFileId(fileId);
        uploadMapper.insertRockImage(rockImage);

        return rockImage; // 返回插入后生成的 rockImageId

    }
    @Transactional
    public Result deleteSpecimen(int specimenId,int userId)
     {
        // 检查 userid 是否匹配
        int matchCount = uploadMapper.checkUserMatch(specimenId, userId);
        if (matchCount == 0) {
            return Result.error("User ID does not match the specimen ID");
        }
        // 查询 rock_image 表中与 specimenId 关联的 file_id 和 image_id
        List<RockImage> rockImages = uploadMapper.findRockImagesBySpecimenId(specimenId);

        // 删除 rock_image 表中的记录
        uploadMapper.deleteRockImagesBySpecimenId(specimenId);

        // 遍历 rockImages，删除 file 表和 image 表中的记录
        for (RockImage rockImage : rockImages) {
            Integer fileId = rockImage.getFileId();
            Integer imageId = rockImage.getImageId();

            // 根据 file_id 查询 file_path，并操作 OBS
            String filePath = uploadMapper.findFilePathById(fileId);
            huaweiObsUtil.deleteFileByUrl(filePath);
            log.info("huaweiobs 删除成功");
            // 删除 file 表中的记录
            uploadMapper.deleteFileById(fileId);
            // 删除 image 表中的记录
            uploadMapper.deleteImageById(imageId);
        }

        // 删除 specimen 表中的记录
        uploadMapper.deleteSpecimenById(specimenId);
        return Result.success("删除成功");
    }



}

