package com.demo.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@ServletComponentScan
@SpringBootApplication
@EnableCaching//SpringBoot中启用redis，只需要在Application主类上添加@EnableCaching注解，之后在需要启用缓存的查询方法上添加@Cacheable注解。
public class SocketServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocketServerApplication.class, args);
	}
}
