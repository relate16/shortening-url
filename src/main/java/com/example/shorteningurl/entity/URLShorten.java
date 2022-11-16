package com.example.shorteningurl.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class URLShorten {
	
	@Id @GeneratedValue
	private Long id;
	
	private String originalURL;

	public URLShorten(String originalURL) {
		this.originalURL = originalURL;
	}
	

}
