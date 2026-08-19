package com.xsdq.polaris;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@MapperScan("com.xsdq.polaris.repository.dao")
@SpringBootApplication
public class PolarisApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolarisApplication.class, args);
	}

}
