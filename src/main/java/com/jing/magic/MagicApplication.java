package com.jing.magic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jing.magic.mapper")
public class MagicApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagicApplication.class, args);
    }

}
