package com.poc.springaidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages ={"com.poc.springaidemo"})
public class SpringaidemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringaidemoApplication.class, args);
	}

}
