package com.foxminded.javaspring.universitycms.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.PersonDao;
import com.foxminded.javaspring.universitycms.model.Person;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class PersonService {

	private PersonDao personDao;

	@Autowired
	public PersonService(PersonDao personDao) {
		this.personDao = personDao;
	}

	public Person saveNewPerson(Person person) {
		var savingPerson = personDao.save(person);
		log.info("New person " + person.getFirstName() + " " + person.getLastName() + " saved");
		return savingPerson;
	}
	
	public Person findPersonById(Long personId) {
		var person = personDao.findById(personId);
		if (person.isPresent()) {
			log.info("Person with Id" + personId + "found");
			return person.get();
		}
		log.info("Person with Id" + personId + " not found");
		return null;
	}
	
	public List<Person> findAllPersons(){
		return personDao.findAll();
	}
	
	public Person updatePerson(Person person) {
		var updatingPerson = personDao.findById(person.getPersonID());
		if (updatingPerson.isPresent()) {
			personDao.save(person);
			log.info("Person" + person.getFirstName() + " " + person.getLastName() + " updated");
			return person;
		}
		log.info("This person does not exists in the database");
		return null;
	}
	
	public void deletePersonById(Long personId) {
		personDao.deleteById(personId);
		log.info("Person with Id " + personId + " deleted");
	}
}
