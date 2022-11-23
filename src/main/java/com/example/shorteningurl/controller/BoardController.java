package com.example.shorteningurl.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.shorteningurl.dto.UrlDto;
import com.example.shorteningurl.service.UrlDtoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {
	
	private final UrlDtoService urlDtoService;
	
	@GetMapping("/board-list") 
	public String getTest(@ModelAttribute UrlDto urlDto, Model model) {		
		List<UrlDto> urlDtos = urlDtoService.findUrlDtos();
		model.addAttribute("datas", urlDtos);
		return "board/board-list"; 
	}

}
