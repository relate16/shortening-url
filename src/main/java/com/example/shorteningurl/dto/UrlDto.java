package com.example.shorteningurl.dto;

import org.hibernate.validator.constraints.URL;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlDto {
	
	@URL
	String originalUrl;
	String shortUrl;
	
}
