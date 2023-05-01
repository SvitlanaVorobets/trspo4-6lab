package com.example.lab3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Lab3ApplicationPayments {

	public static void main(String[] args) {
		SpringApplication.run(Lab3ApplicationPayments.class, args);
	}

}
