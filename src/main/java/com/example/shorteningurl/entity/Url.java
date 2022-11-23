package com.example.shorteningurl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {
	
	@Id @GeneratedValue
	private Long id;
	
	@Column(length = 2083, nullable = false)
	private String originalUrl;

	public Url(String originalURL) {
		this.originalUrl = originalURL;
	}
	

}
