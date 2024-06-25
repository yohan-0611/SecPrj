package com.avi6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ForTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForTestApplication.class, args);
	}

}
