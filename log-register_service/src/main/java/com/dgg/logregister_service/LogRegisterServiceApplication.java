package com.dgg.logregister_service;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.dgg")
@DubboComponentScan("com.dgg.serviceimpl")
@MapperScan("com.dgg.dao")
public class LogRegisterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogRegisterServiceApplication.class, args);
	}

}
