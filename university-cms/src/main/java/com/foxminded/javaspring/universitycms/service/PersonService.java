package com.foxminded.javaspring.universitycms.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.PersonDao;

@Service
@Transactional
public class PersonService {
	
	private PersonDao personDao;

	@Autowired
	public PersonService(PersonDao personDao) {
		this.personDao = personDao;
	}

}
