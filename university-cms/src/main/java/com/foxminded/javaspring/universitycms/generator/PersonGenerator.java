package com.foxminded.javaspring.universitycms.generator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.PersonDao;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Role;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PersonGenerator {
	
	private Random random;
	private List<String> studentFirstNames = Arrays.asList("Lexi", "Elouise", "Wilbur", "Glenda", "Judah", "Salahuddin",
			"Juliet", "Tanner", "Luella", "Enid", "Hadiya", "Rares", "Bryan", "Patsy", "Eshan", "Lester", "Bentley",
			"Yu", "Finlay", "Sylvie");
	private List<String> studentLastNames = Arrays.asList("Ferry", "Buck", "Moody", "Craft", "Ridley", "Aguilar",
			"Garrett", "Peralta", "Mcknight", "O'Quinn", "Simons", "Kelley", "Trejo", "Dougherty", "Palacios", "Murphy",
			"Gordon", "Mcgee", "Strong", "Philip");
	private PersonDao personDao;
	
	@Autowired
	public PersonGenerator(Random random, PersonDao personDao) {
		this.random = random;
		this.personDao = personDao;
	}
	
	public void generateNPersonsForStudent(int countToGenerate) {
		IntStream.rangeClosed(1, countToGenerate).forEach(personForStudentID -> personDao.save(createPersonForStudent()));
		log.info(countToGenerate + " persons for students generated");
	}
	
	public void generateNPersonsForTeacher(int countToGenerate) {
		IntStream.rangeClosed(1, countToGenerate).forEach(personForTeacherID -> personDao.save(createPersonForTeacher()));
		log.info(countToGenerate + " persons for teachers generated");
	}
	
	private Person createPersonForStudent() {
		Person personForStudent = new Person();
		personForStudent.setFirstName(getRandomFirstName());
		personForStudent.setLastName(getRandomLastName());
		personForStudent.setLogin("StudentLogin");
		personForStudent.setPassword("StudentPassword");
		personForStudent.setRole(Role.STUDENT);
		return personForStudent;
	}
	
	private Person createPersonForTeacher() {
		Person personForTeacher = new Person();
		personForTeacher.setFirstName(getRandomFirstName());
		personForTeacher.setLastName(getRandomLastName());
		personForTeacher.setLogin("TeacherLogin");
		personForTeacher.setPassword("TeacherPassword");
		personForTeacher.setRole(Role.TEACHER);
		return personForTeacher;
	}
	
	private String getRandomFirstName() {
		return studentFirstNames.get(random.nextInt(studentFirstNames.size()-1));
	}

	private String getRandomLastName() {
		return studentLastNames.get(random.nextInt(studentLastNames.size()-1));
	}

}
