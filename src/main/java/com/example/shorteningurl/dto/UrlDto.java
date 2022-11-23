package com.example.shorteningurl.dto;

import org.hibernate.validator.constraints.URL;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlDto {
	
	Long id;
	String originalUrl;
	String shortUrl;
	
	public UrlDto(Long id, String originalUrl) {
		super();
		this.id = id;
		this.originalUrl = originalUrl;
	}
	
	public UrlDto(String originalUrl, String shortUrl) {
		this.originalUrl = originalUrl;
		this.shortUrl = shortUrl;
	}


	
	
	
}
