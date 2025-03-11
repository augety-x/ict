package com.ftt.mapper;

import com.ftt.annotation.AutoFill;
import com.ftt.dto.ImageRequestDTO;
import com.ftt.entity.File;
import com.ftt.entity.Image;
import com.ftt.entity.RockImage;
import com.ftt.entity.Specimen;
import com.ftt.enumeration.OperationType;
import com.ftt.vo.ImageResponseVOO;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface UploadMapper  {



     RockImage selectRockImageById(int id);

     @AutoFill(OperationType.INSERT)
     int insertFile(File file);
     @AutoFill(OperationType.INSERT)
     int insertImage(Image image);
     @AutoFill(OperationType.INSERT)
     int insertRockImage(RockImage rockImage);
     @AutoFill(OperationType.INSERT)
     int insertSpecimen(Specimen specimen);


     Integer findIdByIdentification(String identification);

     Integer findIdByCategoryName(String categoryName);

     String selectImagePathById(int id);
     String selectCategoryNameById(int id);
     String selectSpecimenNameById(int id);



     // 检查 userid 是否匹配
     @Select("SELECT COUNT(*) FROM specimen WHERE id = #{specimenId} AND user_id = #{userId}")
     int checkUserMatch(@Param("specimenId") Integer specimenId, @Param("userId") Integer userId);

     // 删除 specimen 表中的记录
// 使用 @Delete 注解来指定 SQL 删除语句
// SQL 语句：DELETE FROM specimen WHERE id = #{specimenId}
// 其中 #{specimenId} 是一个占位符，会被方法参数中的 specimenId 值替换
     @Delete("DELETE FROM specimen WHERE id = #{specimenId}")
// 定义一个接口方法 deleteSpecimenById，用于删除指定 id 的 specimen 记录
// 方法的返回类型是 int，表示删除操作影响的行数
     int deleteSpecimenById(@Param("specimenId") Integer specimenId);

     // 查询 rock_image 表中与 specimenId 关联的 file_id 和 image_id
     @Select("SELECT file_id, image_id FROM rock_image WHERE specimen_id = #{specimenId}")
     List<RockImage> findRockImagesBySpecimenId(@Param("specimenId") Integer specimenId);

     // 删除 rock_image 表中的记录
     @Delete("DELETE FROM rock_image WHERE specimen_id = #{specimenId}")
     int deleteRockImagesBySpecimenId(@Param("specimenId") Integer specimenId);

     // 删除 file 表中的记录
     @Delete("DELETE FROM file WHERE id = #{fileId}")
     int deleteFileById(@Param("fileId") Integer fileId);

     // 删除 image 表中的记录
     @Delete("DELETE FROM image WHERE id = #{imageId}")
     int deleteImageById(@Param("imageId") Integer imageId);

     // 根据 file_id 查询 file_path
     @Select("SELECT file_path FROM file WHERE id = #{fileId}")
     String findFilePathById(@Param("fileId") Integer fileId);
     List<ImageResponseVOO> findImagesByCriteria(ImageRequestDTO request);

}


