package com.ftt.utils;


import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@Slf4j
public class HuaweiObsUtil {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    /**
     * 文件上传
     *
     * @param bytes
     * @param objectName
     * @return
     */
    public String upload(byte[] bytes, String objectName) {

        objectName = "images/"+objectName;
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(accessKeyId, accessKeySecret, endpoint);
        log.info("文件"+objectName+"上传成功");
        try {
            // 创建PutObject请求
            obsClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
        } catch (ObsException oe) {
            System.out.println("Caught an ObsException, which means your request made it to OBS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getErrorRequestId());
            System.out.println("Host ID:" + oe.getErrorHostId());
        } catch (Exception e) {
            System.out.println("Caught an Exception, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OBS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + e.getMessage());
        } finally {
            if (obsClient != null) {
                try {
                    obsClient.close();
                } catch (Exception e) {
                    log.error("Error closing OBS client", e);
                }
            }
        }

        // 文件访问路径规则 https://BucketName.Endpoint/ObjectName
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append(objectName);

        log.info("文件上传到:{}", stringBuilder.toString());

        return stringBuilder.toString();
    }
    public void deleteFileByUrl(String fileUrl) {
        // 解析文件路径，提取出 bucketName 和 objectName
        String regex = "https://([^.]+)\\.obs\\.(.+)/(.+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileUrl);

        if (matcher.find()) {
            String bucketName = matcher.group(1); // ict-bucket-8a36
            String objectName = matcher.group(3); // images/thumb_c184a7b0-2a40-43aa-b045-8b1440677f4d.jpg

            System.out.println("bucketName: " + bucketName);
            System.out.println("objectName: " + objectName);

            // 创建ObsClient实例
            ObsClient obsClient = new ObsClient(accessKeyId, accessKeySecret, endpoint);
            try {
                // 删除单个对象
                obsClient.deleteObject(bucketName, objectName);
                log.info("文件删除成功: {}{}", bucketName,objectName);
            } catch (ObsException e) {
                log.error("文件删除失败: {}", fileUrl);
                log.error("HTTP Code: {}", e.getResponseCode());
                log.error("Error Code: {}", e.getErrorCode());
                log.error("Error Message: {}", e.getErrorMessage());
                log.error("Request ID: {}", e.getErrorRequestId());
                log.error("Host ID: {}", e.getErrorHostId());
            } catch (Exception e) {
                log.error("文件删除失败: {}", fileUrl);
                e.printStackTrace();
            } finally {
                if (obsClient != null) {
                    try {
                        obsClient.close();
                    } catch (Exception e) {
                        log.error("Error closing OBS client", e);
                    }
                }
            }
        } else {
            log.error("Invalid file URL: {}", fileUrl);
        }
    }
}