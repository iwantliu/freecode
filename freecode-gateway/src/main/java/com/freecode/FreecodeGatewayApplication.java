package com.freecode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FreecodeGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreecodeGatewayApplication.class, args);
	}

}
