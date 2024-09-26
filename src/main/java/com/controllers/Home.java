package com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class Home {

	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/new_idcard")
	public String newIDCard() {
		return "new-idcard";
	}
	
	@GetMapping("/preview")
	public String idCardPreview() {
		
		return "idcardpreview";
	}
	
	
}
