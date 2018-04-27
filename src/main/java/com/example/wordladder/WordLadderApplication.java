package com.example.wordladder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
public class WordLadderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordLadderApplication.class, args);
	}
}
