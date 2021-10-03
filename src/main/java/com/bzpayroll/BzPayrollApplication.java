package com.bzpayroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan("com.bzpayroll")
@ComponentScan("com.bzpayroll.login.controller")
@SpringBootApplication
@EnableAutoConfiguration
public class BzPayrollApplication {
 
	public static void main(String[] args) {
		SpringApplication.run(BzPayrollApplication.class, args);
	}

}
