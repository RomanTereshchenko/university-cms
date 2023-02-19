package com.foxminded.javaspring.universitycms.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.foxminded.javaspring.universitycms.dao.PersonDao;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Role;
import com.foxminded.javaspring.universitycms.service.PersonService;

@SpringBootTest
public class PersonServiceTest {
	private PersonDao personDao;
	private PersonService personService;

	@Autowired
	public PersonServiceTest(PersonDao personDao, PersonService personService) {
		this.personDao = personDao;
		this.personService = personService;
	}

	@Test
	@Transactional
	void testUpdatePerson() throws SQLException {
		Person testPerson = new Person();
		testPerson.setLogin("login");
		testPerson.setPassword("password");
		testPerson.setFirstName("FirstName");
		testPerson.setLastName("LastName");
		testPerson.setRole(Role.STUDENT);
		personDao.save(testPerson);
		testPerson.setLogin("login2");
		personService.updatePerson(testPerson);
		List<Person> persons = personDao.findAll();
		Person savedPerson = persons.get(0);
		assertEquals("login2", savedPerson.getLogin());
		Person testPerson2 = new Person(testPerson.getPersonID() + 1, "login", "password", "FirstName", "LastName",
				Role.STUDENT);
		assertEquals(null, personService.updatePerson(testPerson2));
	}

}
