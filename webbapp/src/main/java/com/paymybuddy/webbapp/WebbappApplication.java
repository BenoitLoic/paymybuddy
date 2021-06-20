package com.paymybuddy.webbapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class WebbappApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebbappApplication.class, args);
	}

}
