package com.example.shorteningurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.shorteningurl.entity.URLShorten;

@SpringBootApplication
public class ShorteningUrlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShorteningUrlApplication.class, args);
	}

}
