package com.example.shorteningurl.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.shorteningurl.dto.UrlDto;
import com.example.shorteningurl.entity.Url;
import com.example.shorteningurl.repository.UrlRepository;
import com.example.shorteningurl.service.UrlDtoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final UrlRepository urlRepsoRepository;
	
	private final UrlDtoService urlDtoService;
	
	@GetMapping("/home")
	public String getHome(@ModelAttribute UrlDto urlDto) {
		return "shortening-url";
	}

	/*
	 * @GetMapping("/test") 
	 * public String getTest(@ModelAttribute UrlDto urlDto, Model model) { 
	 * 		List<UrlDto> urlDtos = urlDtoService.findUrlDtos();
	 * 		model.addAttribute("datas", urlDtos); 
	 * 		return "board/board-list"; 
	 * }
	 */
}
