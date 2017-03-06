package com.packt.webstore.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.springframework.format.datetime.joda.LocalDateTimeParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(){
		return "login";
		
	}
	
	@RequestMapping(value="/loginfailed", method=RequestMethod.GET)
	public String loginerror(Model model){
		model.addAttribute("error", true);
		return "login";
		
	}
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(Model model){
		return "login";
		
	}
	
}
