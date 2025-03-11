package com.board.simpleboardproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SimpleBoardProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleBoardProjectApplication.class, args);
	}

}
