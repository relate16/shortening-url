package com.example.shorteningurl.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.shorteningurl.dto.UrlDto;
import com.example.shorteningurl.service.UrlService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UrlWebController {
	
	private final UrlService urlService;
	private String sitePath = "";
	
	/**
	 * Post매핑인 encodingURL 메서드로 결과창을 띄워버리면
	 * 고객이 새로고침시, 같은 주소 = "/url/encode"를 get방식으로 받기 때문에
	 * 405 오류가 떠서 불편함이 있다.
	 * 따라서 PRG, 즉 redirect 방식 도입해서 썼다.
	 * @variable resultUrlDto : 
	 * 		resultUrlDto.shortUrl = "localhost:8080/{shorUrl}"
	 */
	@PostMapping("/url/encode")
	public String encodingURL(@ModelAttribute UrlDto urlDto, Model model, HttpServletRequest request) throws Exception {
		sitePath = request.getHeader("host") + "/";
		
		String result = urlService.validateUrl(urlDto.getOriginalUrl());

		if(result.equals("접속 가능한 url이 아닙니다.")) {
			model.addAttribute("message", "접속 가능한 url이 아닙니다.");
			return "alert/alert";
		}
		
		Long urlId = urlService.saveUrl(urlDto.getOriginalUrl());
		
		String shortUrl = urlService.getShortUrl(urlId);
		
		UrlDto resultUrlDto = 
				new UrlDto("", sitePath + shortUrl);
		
		model.addAttribute("urlDto", resultUrlDto);
		
		return "redirect:/result?shortUrl="+ shortUrl;
	}
	

	/**
	 * Request Host가 localhost:8080 일 경우,
	 * @throws InterruptedException 
	 * @variable hostPath = localhost:8080/
	 * @variable resolvedPath = //localhost:8080/
	 * @variable fullPath = //localhost:8080/C
	 * @variable model.urlDto.shortUrl=//localhost:8080/C
	 */
	@GetMapping("/result")
	public String showEncodingURL(@ModelAttribute UrlDto urlDto, HttpServletRequest request, Model model) {
		String hostPath = request.getHeader("host") + "/";
		String resolvedPath = resolvePath(hostPath);
		String fullPath = resolvedPath + urlDto.getShortUrl();
		
		urlDto.setShortUrl(fullPath);
		
		model.addAttribute("urlDto", urlDto);
		return "shortening-url-result";
	}


	/**
	 * @method resolvePath 를 쓰는 이유 :
	 * 현재 메서드 decodingURL에서 
	 * return "redirect:" + originalUrl; 으로 쓴다고 가정할 때,
	 * originalUrl이 https://www.naver.com 처럼 full경로이면 
	 * 제대로 네이버 홈페이지에 접속하지만 
	 * originalUrl이 www.naver.com 으로 들어왔을 경우
	 * localhost:8080/www.naver.com 으로 이동하게 된다. 
	 * (리다이렉트일 경우 fullPath면 절대주소로 연결되지만 그게 아니라면 상대주소로 연결되기 때문,
	 * 참고로 상대경로인 redirect: 와 절대경로인 redirect:/는 또 다른 얘기이다.)
	 * 
	 * 이때 해결방법은 originalUrl이 full경로가 아니라면 주소 앞에 //를 붙이는 방법을 쓰는 것이다.
	 * //은 서버 페이지 프로토콜 그대로 전달되어 붙게 한다. 
	 * 예를들어 redirect://www.naver.com; 일 경우 
	 * https 프로토콜 서버라면
	 * https://www.naver.com 으로 이동하게 되는 것이다.
	 * a태그의 href 같은 곳에서도 같은 방법이 적용된다.
	 * href="www.naver.com" 일 경우 localhost:8080/www.naver.com,
	 * href="https://www.naver.com" 일 경우 https://www.naver.com 으로 이동
	 */
	@GetMapping("/{shortUrl}")
	public String decodingURL(@PathVariable String shortUrl) {
		// 63 -> 1 * 62 + 1
		// b -> BASE62[?] = b

		String originalUrl = urlService.getOriginalUrl(shortUrl);
		
		originalUrl = resolvePath(originalUrl);
		
		UrlDto urlDto = new UrlDto(originalUrl, "");

		return "redirect:"+ urlDto.getOriginalUrl();
	}

	/**
	 * https://www.naver.com과 같이 fullPath가 아닐 경우
	 * (예: www.naver.com)
	 * //www.naver.com 으로 경로를 설정해주는 메서드
	 * 
	 * 이 메서드를 쓰는 이유는 현재 클래스의 decodingURL 메서드에 자세히 설명되어 있다.
	 */
	private String resolvePath(String originalUrl) {
		if(!originalUrl.contains("://")) {
			originalUrl = "//" + originalUrl; 
			
		}
		return originalUrl;
	}

}
