package com.foxminded.javaspring.universitycms.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.service.PersonService;

@Controller
@RequestMapping("/index")
public class IndexController {
	
	private PersonService personService;
	
	@Autowired	
	public IndexController(PersonService personService) {
		this.personService = personService;
	}
	
	@GetMapping
	public String showIndex (Model model, Principal principal) {
		String currentUserName = principal.getName();
		Person userPerson = personService.findPersonByLogin(currentUserName);
		model.addAttribute("userPerson", userPerson); 
		return "index";
	}
	
}
