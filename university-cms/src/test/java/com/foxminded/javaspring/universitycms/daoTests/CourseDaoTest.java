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
import com.foxminded.javaspring.universitycms.model.Course;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class CourseDaoTest {
	
	private CourseDao courseDao;
	private GroupDao groupDao;
	private LessonDao lessonDao;
	private PersonDao personDao;
	private StudentDao studentDao;
	private TeacherDao teacherDao;

	@Autowired
	public CourseDaoTest(CourseDao courseDao, GroupDao groupDao, LessonDao lessonDao, PersonDao personDao,
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
		courseDao.deleteAll();
		groupDao.deleteAll();
		log.info("Database cleaned");
	}
	
	@Test
	@Transactional
	void testSaveAndFindAll() {
		Course course = new Course();
		course.setCourseName("TestCourse");
		courseDao.save(course);
		Course savedCourse = courseDao.findAll().get(0);
		assertNotNull(savedCourse);
		assertEquals("TestCourse", savedCourse.getCourseName());
	}
	
	@Test
	@Transactional
	void testFindById() {
		Course course = new Course();
		course.setCourseName("TestCourse");
		courseDao.save(course);
		Course savedCourse = courseDao.findAll().get(0);
		Long savedCourseId = savedCourse.getCourseID();
		Course foundCourse = courseDao.findById(savedCourseId).get();
		assertNotNull(foundCourse);
		assertEquals(savedCourseId, foundCourse.getCourseID());
	}

	@Test
	@Transactional
	void testDeleteById() {
		Course course = new Course();
		course.setCourseName("TestCourse");
		courseDao.save(course);
		Course savedCourse = courseDao.findAll().get(0);
		Long savedCourseId = savedCourse.getCourseID();
		Course foundCourse = courseDao.findById(savedCourseId).get();
		assertNotNull(foundCourse);
		assertEquals(savedCourseId, foundCourse.getCourseID());
		courseDao.deleteById(savedCourseId);
		Optional<Course> deletedCourse = courseDao.findById(savedCourseId);
		assertEquals((Object)deletedCourse, Optional.empty());
	}
}
