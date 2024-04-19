package com.javateam.healthyFoodProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class DemoController {
	
	@GetMapping("demo")
	public String demo() {
		
		log.info("demo");
		
		return "demo";
	}

}
