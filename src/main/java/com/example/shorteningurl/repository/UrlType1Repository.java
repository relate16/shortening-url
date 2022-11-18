package com.example.shorteningurl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shorteningurl.entity.UrlType1;

@Repository
public interface UrlType1Repository extends JpaRepository<UrlType1, Long> {
	
}
