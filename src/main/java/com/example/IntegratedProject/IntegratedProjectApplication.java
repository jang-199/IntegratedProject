package com.example.IntegratedProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class IntegratedProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegratedProjectApplication.class, args);
	}

}
