package com.packt.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.packt.webstore.domain.Product;
import com.packt.webstore.service.OrderService;
@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	@RequestMapping("/order/P1235/2")
	public String process() {
		orderService.processOrder("P1235", 42);
		return "redirect:/products";

	}	
	
}
