package com.foxminded.javaspring.universitycms.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.javaspring.universitycms.dao.PersonDao;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.service.PersonService;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
	
	@Mock
	private PersonDao personDao;
	
	@InjectMocks
	private PersonService personService;
	
	@Test
	void testSaveNewPerson() throws SQLException {
		Person person = new Person();
		person.setFirstName("TestName");
		Mockito.when(personDao.save(any(Person.class))).thenReturn(person);
		Person savedPerson = personService.saveNewPerson(person);
		assertEquals("TestName", savedPerson.getFirstName());
	}
	
	@Test
	void testFindPersonById() throws SQLException {
		Person savedPerson = new Person();
		savedPerson.setPersonID(1L);
		Mockito.when(personDao.findById(savedPerson .getPersonID())).thenReturn(Optional.of(savedPerson ));
		Person foundPerson = personService.findPersonById(savedPerson.getPersonID());
		assertEquals(1L, foundPerson.getPersonID());
		Person notFoundPerson = personService.findPersonById(0L);
		assertEquals(null, notFoundPerson);
	}
	
	@Test
	void testFindAllPersons() throws SQLException {
		List<Person> allPersonsList = new ArrayList<>();
		Person person = new Person();
		person.setFirstName("TestName");
		allPersonsList.add(person);
		Mockito.when(personDao.findAll()).thenReturn(allPersonsList);
		List<Person> foundPersons = personService.findAllPersons();
		assertEquals("TestName", foundPersons.get(0).getFirstName());
	}
	
	@Test
	void testUpdatePerson() throws SQLException {
		Person dBPerson = new Person();
		dBPerson.setPersonID(1L);
		Person updatingPerson = new Person();
		updatingPerson.setPersonID(dBPerson.getPersonID());
		updatingPerson.setFirstName("TestName");
		Person nonUpdatingPerson = new Person();
		nonUpdatingPerson.setPersonID(0L);
		Mockito.when(personDao.findById(dBPerson.getPersonID())).thenReturn(Optional.of(dBPerson));
		Person updatedPerson = personService.updatePerson(updatingPerson);
		assertEquals("TestName", updatedPerson.getFirstName());
		Person nonUpdatedPerson = personService.updatePerson(nonUpdatingPerson);
		assertEquals(null, nonUpdatedPerson);
	}
	
	@Test
	void testDeletePerson() throws SQLException {
		personService.deletePersonById(anyLong());
		Mockito.verify(personDao).deleteById(anyLong());
	}

}
