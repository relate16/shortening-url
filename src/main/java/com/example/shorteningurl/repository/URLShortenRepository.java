package com.example.shorteningurl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shorteningurl.entity.URLShorten;

@Repository
public interface URLShortenRepository extends JpaRepository<URLShorten, Long> {
	
}
