package com.dictation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebMvcConfigurerImpl implements WebMvcConfigurer {
	
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") //모든 요청에 대해서
				.allowedMethods("GET", "POST", "HEAD", "PUT", "DELETE", "OPTIONS")
				.allowedOrigins("http://localhost:8080/"); // 이 로컬로스트를 허용한다.
	}
	
	
	

}
