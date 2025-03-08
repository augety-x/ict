package com.ftt.ict;

import com.ftt.tests.Tests;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@SpringBootApplication
@EnableTransactionManagement
@Slf4j
public class IctApplication {

    public static void main(String[] args) {
        Tests tets = new Tests();
        tets.test();
        SpringApplication.run(IctApplication.class, args);
    }

}
