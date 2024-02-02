package com.github.ong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CatUploadFile2Web {

    public static void main(String[] args) {
        SpringApplication.run(CatUploadFile2Web.class, args);
    }
}
