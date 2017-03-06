package com.packt.webstore.controller;

import org.omg.CORBA.Request;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	//@RequestMapping(value="/")
	@RequestMapping(value="/welcome/greeting")
	public String greeting() {
		return "welcome";
		
	}
	
	
	@RequestMapping(value="/")
	public String welcome(Model model) {
		model.addAttribute("greeting", "Welcome to web store!");
		model.addAttribute("tagline", "The one and only amazing webstore!");
	
		
		return "forward:/welcome/greeting";
	}

}
