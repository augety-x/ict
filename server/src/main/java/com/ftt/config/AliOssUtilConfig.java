//package com.ftt.config;
//
//import com.ftt.properties.AliOssProperties;
//import com.ftt.utils.AliOssUtil;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class AliOssUtilConfig {
//
//    @Bean
//    @ConditionalOnMissingBean
//    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties)
//    {
//        return new AliOssUtil(aliOssProperties.getEndpoint(), aliOssProperties.getAccessKeyId(), aliOssProperties.getAccessKeySecret(), aliOssProperties.getBucketName());
//    }
//
//}
