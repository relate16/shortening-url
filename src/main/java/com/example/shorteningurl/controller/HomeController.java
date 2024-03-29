package com.example.shorteningurl.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.shorteningurl.dto.UrlDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	@GetMapping("/home")
	public String getHome(@ModelAttribute UrlDto urlDto) {
		return "shortening-url";
	}

}
