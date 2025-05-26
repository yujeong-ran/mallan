package com.mallan.yujeongran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MallanApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallanApplication.class, args);
    }
}
