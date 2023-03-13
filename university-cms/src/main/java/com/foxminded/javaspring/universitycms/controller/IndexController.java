package com.foxminded.javaspring.universitycms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/index")
public class IndexController {


	@GetMapping
	public @ResponseBody String findAll(Model model) {
		return "homePage/index";
	}

	@GetMapping("/all")
	public String showAll() {
		return "index";
	}
}
