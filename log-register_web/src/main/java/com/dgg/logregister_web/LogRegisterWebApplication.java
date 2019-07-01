package com.dgg.logregister_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.dgg")
public class LogRegisterWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogRegisterWebApplication.class, args);
	}

}
