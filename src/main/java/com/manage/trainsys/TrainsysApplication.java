package com.manage.trainsys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.manage.trainsys.mapper")
public class TrainsysApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainsysApplication.class, args);
    }

}
