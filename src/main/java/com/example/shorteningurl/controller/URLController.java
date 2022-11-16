package com.example.shorteningurl.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shorteningurl.dto.URLShortenDto;
import com.example.shorteningurl.entity.URLShorten;
import com.example.shorteningurl.repository.URLShortenRepository;
import com.example.shorteningurl.service.URLShortenService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class URLController {

	private final URLShortenService urlShortenService;

	@PostMapping("/url/incode")
	public Result encodingURL(String originalURL) {
		
		String sitePath = "/url/decode/";
		
		Long urlShortenId = 
				urlShortenService.saveURLShorten(originalURL);

		String shortURL = urlShortenService.getShortURL(urlShortenId);
		
		URLShortenDto urlShortenDto = 
				new URLShortenDto(sitePath + shortURL);
		
		return new Result<URLShortenDto>(urlShortenDto);

	}

	@GetMapping("/url/decode/{shortURL}")
	public Result decodingURL(@PathVariable String shortURL) {
		// 63 -> 1 * 62 + 1
		// b -> BASE62[?] = b
		
		String originalURL = urlShortenService.getOriginalURL(shortURL);
		
		URLShortenDto urlShortenDto = new URLShortenDto(originalURL);

		return new Result<URLShortenDto>(urlShortenDto);
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class Result<T> {
		private T data;
	}

}
