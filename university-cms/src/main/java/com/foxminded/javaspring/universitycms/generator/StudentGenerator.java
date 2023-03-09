package com.foxminded.javaspring.universitycms.generator;

import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.universitycms.dao.GroupDao;
import com.foxminded.javaspring.universitycms.dao.PersonDao;
import com.foxminded.javaspring.universitycms.dao.StudentDao;
import com.foxminded.javaspring.universitycms.model.Group;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Role;
import com.foxminded.javaspring.universitycms.model.Student;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class StudentGenerator {

	private Random random;
	private PersonDao personDao;
	private StudentDao studentDao;
	private GroupDao groupDao;

	@Autowired
	public StudentGenerator(Random random, PersonDao personDao, StudentDao studentDao, GroupDao groupDao) {
		this.random = random;
		this.personDao = personDao;
		this.studentDao = studentDao;
		this.groupDao = groupDao;
	}

	public void generateStudents() {
		List<Person> persons = personDao.findAll();
		List<Group> groups = groupDao.findAll();
		for (Person person : persons) {
			if (person.getRole() == Role.STUDENT) {
			Student student = new Student();
			student.setPerson(person);
			student.setGroup(groups.get(random.nextInt(groups.size()-1)));
			studentDao.save(student);}
		}
		log.info("Students generated");
	}

}
