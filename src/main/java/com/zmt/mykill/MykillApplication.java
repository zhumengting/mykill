package com.zmt.mykill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.zmt.mykill.mapper")
public class MykillApplication {
    public static void main(String[] args) {
        SpringApplication.run(MykillApplication.class, args);
    }
}
