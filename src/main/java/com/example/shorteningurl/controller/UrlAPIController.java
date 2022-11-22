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
public class UrlAPIController {

//	private final UrlService urlService;

	/**
	 * 원본 url을 짧은 url로 변환하는 메서드
	 * @variable shorURL = B 혹은 BCDSAE 같이 문자만 반환한다. 
	 * 					   /B 혹은 /BCDSAE 처럼 슬래쉬 포함 x
	 */
//	@PostMapping("/url/encode")
//	public Result encodingURL(@RequestBody UrlDto urlDto) throws Exception {
//		
//		String sitePath = "/";
//		
//		Long urlId = urlService.saveUrl(urlDto.getOriginalUrl());
//		
//		String shortURL = urlService.getShortUrl(urlId);
//		
//		UrlDto resultUrlDto = 
//				new UrlDto("", sitePath + shortURL);
//		
//		return new Result<UrlDto>(resultUrlDto);
//	}
//
//
//	@GetMapping("/{shortUrl}")
//	public Result decodingURL(@PathVariable String shortUrl) {
//		// 63 -> 1 * 62 + 1
//		// b -> BASE62[?] = b
//		
//		String originalUrl = urlService.getOriginalUrl(shortUrl);
//		UrlDto urlDto = new UrlDto(originalUrl, "");
//
//		return new Result<UrlDto>(urlDto);
//	}
//
//	@Data
//	@NoArgsConstructor
//	@AllArgsConstructor
//	static class Result<T> {
//		private T data;
//	}
	
}
