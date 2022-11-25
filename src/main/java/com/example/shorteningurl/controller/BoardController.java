package com.example.shorteningurl.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shorteningurl.dto.UrlDto;
import com.example.shorteningurl.service.UrlDtoService;
import com.example.shorteningurl.service.UrlService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {
	
	private final UrlService urlService;
	private final UrlDtoService urlDtoService;
	
	@GetMapping("/board-list") 
	public String getBoard(@ModelAttribute UrlDto urlDto, Model model) {		
		List<UrlDto> urlDtos = urlDtoService.findUrlDtos();
		model.addAttribute("datas", urlDtos);

		return "board/board-list"; 
	}
	
	@PostMapping("/board-list")
	public String deleteUrl(@RequestParam Long urlId, @ModelAttribute UrlDto urlDto) {
		urlService.deleteUrl(urlId);

		return "redirect:/board-list";
	}

}
