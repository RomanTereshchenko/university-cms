package com.foxminded.javaspring.universitycms.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import com.foxminded.javaspring.universitycms.dao.PersonDao;
import com.foxminded.javaspring.universitycms.dao.TeacherDao;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Role;
import com.foxminded.javaspring.universitycms.model.Teacher;
import com.foxminded.javaspring.universitycms.service.TeacherService;

@SpringBootTest
class TeacherServiceTest {
	private TeacherDao teacherDao;
	private PersonDao personDao;
	private TeacherService teacherService;

	@Autowired
	public TeacherServiceTest(TeacherDao teacherDao, PersonDao personDao, TeacherService teacherService) {
		this.teacherDao = teacherDao;
		this.personDao = personDao;
		this.teacherService = teacherService;
	}

	@Test
	@Transactional
	@WithMockUser(username="test",roles={"ADMIN"})
	void testUpdateTeacher() throws SQLException {
		Teacher testTeacher = new Teacher();
		Person person = new Person();
		person.setLogin("login");
		person.setPassword("password");
		person.setFirstName("FirstName");
		person.setLastName("LastName");
		person.setRole(Role.TEACHER);
		personDao.save(person);
		testTeacher.setPerson(person);
		testTeacher.setCourses(new HashSet<>());
		teacherDao.save(testTeacher);
		person.setLogin("login2");
		personDao.save(person);
		testTeacher.setPerson(person);
		teacherService.updateTeacher(testTeacher);
		List<Teacher> teachers = teacherDao.findAll();
		Teacher savedTeacher = teachers.get(0);
		assertEquals("login2", savedTeacher.getPerson().getLogin());
		Teacher testTeacher2 = new Teacher(testTeacher.getTeacherID() + 1, person, new HashSet<>());
		assertEquals(null, teacherService.updateTeacher(testTeacher2));
	}

}
