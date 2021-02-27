package com.JWTSptingWebApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class JwtSptingWebAppApplication {
	
	static Logger log = LoggerFactory.getLogger(JwtSptingWebAppApplication.class);
	//
	public static void main(String[] args) {
		log.info("log INFO {}","JwtSptingWebAppApplication Starting..");
		SpringApplication.run(JwtSptingWebAppApplication.class, args);
		log.info("log INFO {}","JwtSptingWebAppApplication Started..");
	}
}