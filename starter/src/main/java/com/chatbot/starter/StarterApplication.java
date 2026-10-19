package com.chatbot.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@MapperScan("com.chatbot.mapper")
//@SpringBootApplication(scanBasePackages = {"com.chatbot", "com.chatbot.service"})
public class StarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarterApplication.class, args);
    }

}
