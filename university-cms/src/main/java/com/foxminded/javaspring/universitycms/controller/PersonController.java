package com.foxminded.javaspring.universitycms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.service.PersonService;

@Controller
@RequestMapping("/persons")
public class PersonController {

	@Autowired
	private PersonService personService;

	@GetMapping
	public @ResponseBody List<Person> findAll() {
		return personService.findAllPersons();
	}

	@GetMapping("/all")
	public String showAll(Model model) {
		List<Person> persons = personService.findAllPersons();
		model.addAttribute("persons", persons);
		return "persons";
	}

}
