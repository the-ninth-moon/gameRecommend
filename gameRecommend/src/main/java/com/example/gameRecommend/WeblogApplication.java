package com.example.gameRecommend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.example.gameRecommend.mapper")
@EnableTransactionManagement //开启事务支持
public class WeblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeblogApplication.class, args);
    }

}
