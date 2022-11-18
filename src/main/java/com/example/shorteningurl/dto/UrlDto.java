package com.example.shorteningurl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlDto {
	String originalUrl;
	String shorUrl;
	
}
