package com.chatue.bookverse.bookverse_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouteController {

	
	//Lien vers le formulaire de connexion 
	@GetMapping("/login")
	public String toLogin() {
		return "login";
	}
	
	//Lien vers le formulaire de  deconnexion
	@GetMapping("/logout")
	public String toLogout() {
		return "logout";
	}
}
