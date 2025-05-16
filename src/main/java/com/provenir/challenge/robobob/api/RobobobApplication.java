package com.provenir.challenge.robobob.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.provenir.challenge.robobob")
public class RobobobApplication {

	public static void main(String[] args) {
		SpringApplication.run(RobobobApplication.class, args);
	}

}
