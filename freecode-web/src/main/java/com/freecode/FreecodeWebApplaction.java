package com.freecode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p>Description:</p>
 *
 * @author stliu
 * @version 1.0
 * @createtime 2019-07-18 17:27
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FreecodeWebApplaction {
    public static void main(String[] args) {
        SpringApplication.run(FreecodeWebApplaction.class);
    }
}
