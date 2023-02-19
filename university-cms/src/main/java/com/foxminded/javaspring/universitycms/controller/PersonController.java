package com.foxminded.javaspring.universitycms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foxminded.javaspring.universitycms.dao.PersonDao;
import com.foxminded.javaspring.universitycms.model.Person;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
	
	@Autowired
    private PersonDao personDao;

    @GetMapping
    public List<Person> findAll() {
        return personDao.findAll();
    }

}
