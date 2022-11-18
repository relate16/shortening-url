package com.example.shorteningurl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.shorteningurl.dto.UrlDto;
import com.example.shorteningurl.service.UrlService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UrlController {

	private final UrlService urlService;

	@ResponseBody
	@PostMapping("/url/encode")
	public Result encodingURL(@RequestBody UrlDto urlDto) throws Exception {
		
		String sitePath = "/url/decode/";
		
		Long urlId = urlService.saveUrl(urlDto.getOriginalUrl());
		
		String shortURL = urlService.getShortUrl(urlId);
		
		UrlDto resultUrlDto = 
				new UrlDto("", sitePath + shortURL);
		
		return new Result<UrlDto>(resultUrlDto);
	}


	@ResponseBody
	@GetMapping("/url/decode/{shortUrl}")
	public Result decodingURL(@PathVariable String shortUrl) {
		// 63 -> 1 * 62 + 1
		// b -> BASE62[?] = b
		
		String originalUrl = urlService.getOriginalUrl(shortUrl);
		UrlDto urlDto = new UrlDto(originalUrl, "");

		return new Result<UrlDto>(urlDto);
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class Result<T> {
		private T data;
	}
	
	


}
