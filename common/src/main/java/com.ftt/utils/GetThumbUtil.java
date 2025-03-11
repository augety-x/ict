package com.ftt.utils;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class GetThumbUtil {

    public byte[] generateThumbnail(MultipartFile file, int width, int height) throws IOException {
        // 创建一个字节数组输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 生成缩略图并写入字节数组输出流
        Thumbnails.of(file.getInputStream())
                .size(width, height)
                .outputFormat("jpg") // 指定输出格式（如 jpg、png）
                .toOutputStream(outputStream);
        // 返回缩略图的字节数组
        return outputStream.toByteArray();
    }

}
