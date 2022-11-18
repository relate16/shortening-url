package com.example.shorteningurl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.shorteningurl.dto.UrlDto;
import com.example.shorteningurl.service.UrlService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UrlController {

	private final UrlService urlService;

	@PostMapping("/url/incode")
	public Result encodingURL(@RequestBody UrlDto urlDto) {
		
		String sitePath = "/url/decode/";
		
		String shortURL = validateLength(urlDto.getOriginalUrl());
			
		UrlDto resultUrlDto = 
				new UrlDto("", sitePath + shortURL);
		
		return new Result<UrlDto>(resultUrlDto);
		
	}



	@GetMapping("/url/decode/{shortURL}")
	public Result decodingURL(@PathVariable String shortURL) {
		// 63 -> 1 * 62 + 1
		// b -> BASE62[?] = b
		
		String originalURL = urlService.getOriginalURL(shortURL);
		UrlDto urlDto = new UrlDto(originalURL, "");

		return new Result<UrlDto>(urlDto);
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class Result<T> {
		private T data;
	}
	
	
	
	// ↓ private 메서드
	
	private String validateLength(String originalURL) {
		String shortURL;
		
		// select length('가')로 한글길이가 DB에서 어떻게 쓰이는지 확인 했음.
		// h2에서는 한글 한 글자당 영문자와 같은 byte로 쓰임
		if(originalURL.length() < 255) {
			Long urlType1Id = 
					urlService.saveUrlType1(originalURL);
			shortURL = urlService.getShortURL(urlType1Id);
		} else {
			Long urlType2Id = 
					urlService.saveUrlType2(originalURL);
			shortURL = urlService.getShortURL(urlType2Id);
		}
		return shortURL;
	}

}
