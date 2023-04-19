package com.foxminded.javaspring.universitycms.daoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
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
import com.foxminded.javaspring.universitycms.model.Lesson;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class LessonDaoTest {
	
	private CourseDao courseDao; 
	private GroupDao groupDao;
	private LessonDao lessonDao;
	private PersonDao personDao;
	private StudentDao studentDao;
	private TeacherDao teacherDao;

	@Autowired
	public LessonDaoTest(CourseDao courseDao, GroupDao groupDao, LessonDao lessonDao, PersonDao personDao,
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
		Lesson lesson = new Lesson();
		lesson.setLessonDate(LocalDate.of(2000, 1, 1));
		lessonDao.save(lesson);
		Lesson savedLesson = lessonDao.findAll().get(0);
		assertNotNull(savedLesson);
		assertEquals(LocalDate.of(2000, 1, 1), savedLesson.getLessonDate());
	}
	
	@Test
	@Transactional
	void testFindById() {
		Lesson lesson = new Lesson();
		lessonDao.save(lesson);
		Lesson savedLesson = lessonDao.findAll().get(0);
		Long savedLessonId = savedLesson.getLessonID();
		Lesson foundLesson = lessonDao.findById(savedLessonId).get();
		assertNotNull(foundLesson);
		assertEquals(savedLessonId, foundLesson.getLessonID());
	}

	@Test
	@Transactional
	void testDeleteById() {
		Lesson lesson = new Lesson();
		lessonDao.save(lesson);
		Lesson savedLesson = lessonDao.findAll().get(0);
		Long savedLessonId = savedLesson.getLessonID();
		Lesson foundLesson = lessonDao.findById(savedLessonId).get();
		assertNotNull(foundLesson);
		assertEquals(savedLessonId, foundLesson.getLessonID());
		lessonDao.deleteById(savedLessonId);
		Optional<Lesson> deletedLesson = lessonDao.findById(savedLessonId);
		assertEquals((Object)deletedLesson, Optional.empty());
	}
}
