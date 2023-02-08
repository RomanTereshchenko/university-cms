package com.foxminded.javaspring.universitycms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.PersonDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PersonService {
	
	private PersonDao personDao;

	@Autowired
	public PersonService(PersonDao personDao) {
		this.personDao = personDao;
	}

}
