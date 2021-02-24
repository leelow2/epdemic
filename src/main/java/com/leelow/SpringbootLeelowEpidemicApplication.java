package com.leelow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.leelow.mapper")
@SpringBootApplication
@EnableScheduling
public class SpringbootLeelowEpidemicApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootLeelowEpidemicApplication.class, args);
    }

}
