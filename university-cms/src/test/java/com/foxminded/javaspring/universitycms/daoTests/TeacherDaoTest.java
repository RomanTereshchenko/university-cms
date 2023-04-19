package com.foxminded.javaspring.universitycms.daoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.javaspring.universitycms.dao.CourseDao;
import com.foxminded.javaspring.universitycms.dao.GroupDao;
import com.foxminded.javaspring.universitycms.dao.LessonDao;
import com.foxminded.javaspring.universitycms.dao.PersonDao;
import com.foxminded.javaspring.universitycms.dao.StudentDao;
import com.foxminded.javaspring.universitycms.dao.TeacherDao;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Role;
import com.foxminded.javaspring.universitycms.model.Teacher;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class TeacherDaoTest {
	
	private CourseDao courseDao;
	private GroupDao groupDao;
	private LessonDao lessonDao;
	private PersonDao personDao;
	private StudentDao studentDao;
	private TeacherDao teacherDao;

	@Autowired
	public TeacherDaoTest(CourseDao courseDao, GroupDao groupDao, LessonDao lessonDao, PersonDao personDao,
			StudentDao studentDao, TeacherDao teacherDao) {
		this.courseDao = courseDao;
		this.groupDao = groupDao;
		this.lessonDao = lessonDao;
		this.personDao = personDao;
		this.studentDao = studentDao;
		this.teacherDao = teacherDao;
	}

	@BeforeEach
	public void cleanDB() {
		lessonDao.deleteAll();
		studentDao.deleteAll();
		teacherDao.deleteAll();
		personDao.deleteAll();
		groupDao.deleteAll();
		courseDao.deleteAll();
		log.info("Database cleaned");
	}
	
	@Test
	@Transactional
	void testSaveAndFindAll() {
		Person person = new Person();
		person.setLogin("TestLogin");
		person.setPassword("TestPassword");
		person.setFirstName("TestFirstName");
		person.setLastName("TestLastName");
		person.setRole(Role.STUDENT);
		personDao.save(person);
		Teacher teacher = new Teacher();
		teacher.setPerson(person);
		teacherDao.save(teacher);
		Teacher savedTeacher = teacherDao.findAll().get(0);
		assertNotNull(savedTeacher);
		assertEquals("TestFirstName", savedTeacher.getPerson().getFirstName());
	}
	
	@Test
	@Transactional
	void testFindById() {
		Person person = new Person();
		person.setLogin("TestLogin");
		person.setPassword("TestPassword");
		person.setFirstName("TestFirstName");
		person.setLastName("TestLastName");
		person.setRole(Role.STUDENT);
		personDao.save(person);
		Teacher teacher = new Teacher();
		teacher.setPerson(person);
		teacherDao.save(teacher);
		Teacher savedTeacher = teacherDao.findAll().get(0);
		Long savedTeacherId = savedTeacher.getTeacherID();
		Teacher foundTeacher = teacherDao.findById(savedTeacherId).get();
		assertNotNull(foundTeacher);
		assertEquals(savedTeacherId, foundTeacher.getTeacherID());
	}

	@Test
	@Transactional
	void testDeleteById() {
		Person person = new Person();
		person.setLogin("TestLogin");
		person.setPassword("TestPassword");
		person.setFirstName("TestFirstName");
		person.setLastName("TestLastName");
		person.setRole(Role.STUDENT);
		personDao.save(person);
		Teacher teacher = new Teacher();
		teacher.setPerson(person);
		teacherDao.save(teacher);
		Teacher savedTeacher = teacherDao.findAll().get(0);
		Long savedTeacherId = savedTeacher.getTeacherID();
		Teacher foundTeacher = teacherDao.findById(savedTeacherId).get();
		assertNotNull(foundTeacher);
		assertEquals(savedTeacherId, foundTeacher.getTeacherID());
		teacherDao.deleteById(savedTeacherId);
		Optional<Teacher> deletedTeacher = teacherDao.findById(savedTeacherId);
		assertEquals((Object)deletedTeacher, Optional.empty());
	}
}
