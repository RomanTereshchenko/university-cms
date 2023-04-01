package com.foxminded.javaspring.universitycms.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import com.foxminded.javaspring.universitycms.dao.CourseDao;
import com.foxminded.javaspring.universitycms.dao.GroupDao;
import com.foxminded.javaspring.universitycms.dao.LessonDao;
import com.foxminded.javaspring.universitycms.dao.TeacherDao;
import com.foxminded.javaspring.universitycms.model.Course;
import com.foxminded.javaspring.universitycms.model.Group;
import com.foxminded.javaspring.universitycms.model.Lesson;
import com.foxminded.javaspring.universitycms.model.Teacher;
import com.foxminded.javaspring.universitycms.service.LessonService;

@SpringBootTest
public class LessonServiceTest {

	private LessonDao lessonDao;
	private CourseDao courseDao;
	private TeacherDao teacherDao;
	private GroupDao groupDao;
	private LessonService lessonService;

	@Autowired
	public LessonServiceTest(LessonDao lessonDao, CourseDao courseDao, TeacherDao teacherDao, GroupDao groupDao,
			LessonService lessonService) {
		this.lessonDao = lessonDao;
		this.courseDao = courseDao;
		this.teacherDao = teacherDao;
		this.groupDao = groupDao;
		this.lessonService = lessonService;
	}

	@Test
	@Transactional
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void testUpdateLesson() throws SQLException {
		Lesson testLesson = new Lesson();
		testLesson.setLessonDate(LocalDate.of(2015, 1, 20));
		testLesson.setLessonTime(LocalTime.of(8, 0));
		Course course = new Course();
		course.setCourseName("testCourse");
		courseDao.save(course);
		testLesson.setCourse(course);
		Teacher teacher = new Teacher();
		teacherDao.save(teacher);
		testLesson.setTeacher(teacher);
		Group group = new Group();
		group.setGroupName("tt-00");
		groupDao.save(group);
		testLesson.setGroup(group);
		lessonDao.save(testLesson);
		testLesson.setLessonDate(LocalDate.of(2015, 2, 20));
		lessonService.updateLesson(testLesson);
		List<Lesson> lessons = lessonDao.findAll();
		Lesson savedLesson = lessons.get(0);
		assertEquals(LocalDate.of(2015, 2, 20), savedLesson.getLessonDate());
		Lesson testLesson2 = new Lesson(testLesson.getLessonID() + 1, LocalDate.of(2015, 1, 20), LocalTime.of(8, 0),
				new Course(), new Teacher(), new Group());
		assertEquals(null, lessonService.updateLesson(testLesson2));
	}

}
