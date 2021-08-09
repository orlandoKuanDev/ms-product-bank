package com.example.msproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProductApplication.class, args);
    }

}
