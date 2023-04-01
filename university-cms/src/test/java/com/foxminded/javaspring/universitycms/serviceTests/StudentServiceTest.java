package com.foxminded.javaspring.universitycms.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import com.foxminded.javaspring.universitycms.dao.GroupDao;
import com.foxminded.javaspring.universitycms.dao.PersonDao;
import com.foxminded.javaspring.universitycms.dao.StudentDao;
import com.foxminded.javaspring.universitycms.model.Group;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Role;
import com.foxminded.javaspring.universitycms.model.Student;
import com.foxminded.javaspring.universitycms.service.StudentService;

@SpringBootTest
class StudentServiceTest {
	private StudentDao studentDao;
	private PersonDao personDao;
	private GroupDao groupDao;
	private StudentService studentService;

	@Autowired
	public StudentServiceTest(StudentDao studentDao, PersonDao personDao, GroupDao groupDao,
			StudentService studentService) {
		this.studentDao = studentDao;
		this.personDao = personDao;
		this.groupDao = groupDao;
		this.studentService = studentService;
	}

	@Test
	@Transactional
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void testUpdateStudent() throws SQLException {
		Student testStudent = new Student();
		Person person = new Person();
		person.setLogin("login");
		person.setPassword("password");
		person.setFirstName("FirstName");
		person.setLastName("LastName");
		person.setRole(Role.STUDENT);
		personDao.save(person);
		testStudent.setPerson(person);
		Group group = new Group();
		group.setGroupName("tt-00");
		groupDao.save(group);
		testStudent.setGroup(group);
		studentDao.save(testStudent);
		person.setLogin("login2");
		personDao.save(person);
		testStudent.setPerson(person);
		studentService.updateStudent(testStudent);
		List<Student> students = studentDao.findAll();
		Student savedStudent = students.get(0);
		assertEquals("login2", savedStudent.getPerson().getLogin());
		Student testStudent2 = new Student(testStudent.getStudentID() + 1, person, group);
		assertEquals(null, studentService.updateStudent(testStudent2));
	}

}
