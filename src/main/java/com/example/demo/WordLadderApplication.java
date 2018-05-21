package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WordLadderApplication  extends SpringBootServletInitializer{

	@Override
    	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        	return builder.sources(WordLadderApplication.class);
    	}
	
	public static void main(String[] args) {
		SpringApplication.run(WordLadderApplication.class, args);
	}
}
