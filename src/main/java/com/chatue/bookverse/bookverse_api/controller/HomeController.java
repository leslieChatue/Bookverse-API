package com.chatue.bookverse.bookverse_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/")
	public String Home() {
		return "Bienvenue dans BOOKVERSE!";
	}
}
