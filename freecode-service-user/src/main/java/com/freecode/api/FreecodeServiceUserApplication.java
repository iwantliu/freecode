package com.freecode.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FreecodeServiceUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreecodeServiceUserApplication.class, args);
    }

}
