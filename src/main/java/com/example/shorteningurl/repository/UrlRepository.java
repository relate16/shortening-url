package com.example.shorteningurl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shorteningurl.entity.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
	
}
