package com.example.shorteningurl.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shorteningurl.dto.UrlDto;
import com.example.shorteningurl.entity.Url;
import com.example.shorteningurl.repository.UrlRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly=false)
@RequiredArgsConstructor
public class UrlDtoService {
	
	private final UrlRepository urlRepsoRepository;
	
	/**
	 * DB에 있는 모든 Url을 가져와 List<UrlDto>로 반환시켜주는 메서드
	 */
	public List<UrlDto> findUrlDtos() {
		List<Url> urls = urlRepsoRepository.findAll();
		List<UrlDto> urlDtos = convertUrlDtos(urls);
		return urlDtos;
		
	}
	
	/**
	 * List<Url> urls 를 List<UrlDto> urlDtos 로 변환시켜주는 메서드
	 */
	public List<UrlDto> convertUrlDtos(List<Url> urls) {
		List<UrlDto> urlDtos = new ArrayList<>();
		for (Url url : urls) {
			UrlDto result = new UrlDto(url.getId(),url.getOriginalUrl());
			urlDtos.add(result);
		}
		return urlDtos;
	}

}
