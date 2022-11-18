package com.example.shorteningurl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shorteningurl.entity.UrlType2;

@Repository
public interface UrlType2Repository extends JpaRepository<UrlType2, Long> {

}
