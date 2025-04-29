package com.CinephileLog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableCaching
@SpringBootApplication
@EnableJpaAuditing
@MapperScan("com.CinephileLog.mapper")
public class CinephileLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinephileLogApplication.class, args);
    }

}
