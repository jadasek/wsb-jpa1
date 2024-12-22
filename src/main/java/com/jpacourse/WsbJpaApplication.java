package com.jpacourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.jpacourse")
@ComponentScan(basePackages = "com.jpacourse")
public class WsbJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsbJpaApplication.class, args);
	}
}
