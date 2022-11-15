package com.example.shorteningurl.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class Test {
	
	@Id
	Long id;
	
	String username;
	
}
