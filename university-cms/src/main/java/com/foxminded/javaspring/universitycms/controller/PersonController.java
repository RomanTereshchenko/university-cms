package com.foxminded.javaspring.universitycms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxminded.javaspring.universitycms.dto.PersonDto;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.service.PersonService;

@Controller
@RequestMapping("/api/persons")
public class PersonController {
	
	@Autowired
    private PersonService personService;

    @GetMapping
    public @ResponseBody List<Person> findAll() {
        return personService.findAllPersons();
    }
    
    @GetMapping("/all")
    public String showAll(Model model) {
        model.addAttribute("persons", personService.findAllPersons());
        return "persons/allPersons";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        PersonDto personsForm = new PersonDto();

        for (int i = 1; i <= 3; i++) {
            personsForm.addPerson(new Person());
        }

        model.addAttribute("form", personsForm);
        return "persons/createPersonsForm";
    }

}
