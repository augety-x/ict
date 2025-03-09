package com.ftt;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.ftt"})
@MapperScan("com.ftt.mapper")
public class IctApplication {

    public static void main(String[] args) {
        SpringApplication.run(IctApplication.class, args);
    }

}
