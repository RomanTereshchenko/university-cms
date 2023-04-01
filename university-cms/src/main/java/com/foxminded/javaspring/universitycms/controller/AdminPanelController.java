package com.foxminded.javaspring.universitycms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Role;
import com.foxminded.javaspring.universitycms.service.PersonService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminPanelController {

	private PersonService personService;

	@Autowired
	public AdminPanelController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping
	public String showAll() {
		return "adminPanel";
	}

	@PostMapping("/createPerson")
	public String createNewPerson(@RequestParam Map<String, String> personParams) {
		Person person = new Person();
		person.setLogin(personParams.get("login"));
		person.setPassword(personParams.get("password"));
		person.setFirstName(personParams.get("firstName"));
		person.setLastName(personParams.get("lastName"));
		person.setRole(Role.valueOf(personParams.get("role")));
		personService.saveNewPerson(person);
		log.info(person.toString());
		return "adminPanel";
	}
}
