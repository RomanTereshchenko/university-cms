package com.foxminded.javaspring.universitycms.serviceTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.foxminded.javaspring.universitycms.dao.CourseDao;
import com.foxminded.javaspring.universitycms.model.Course;
import com.foxminded.javaspring.universitycms.service.CourseService;

@SpringBootTest
class CourseServiceTest {

	private CourseDao courseDao;
	private CourseService courseService;

	@Autowired
	public CourseServiceTest(CourseDao courseDao, CourseService courseService) {
		this.courseDao = courseDao;
		this.courseService = courseService;
	}

	@Test
	@Transactional
	void testUpdateCourse() throws SQLException {
		Course testCourse = new Course();
		testCourse.setCourseName("TestCourse");
		testCourse.setCourseDescription("Description");
		testCourse.setGroups(new HashSet<>());
		testCourse.setTeachers(new HashSet<>());
		courseDao.save(testCourse);
		testCourse.setCourseName("UpdatedTestCourse");
		courseService.updateCourse(testCourse);
		List<Course> courses = courseDao.findAll();
		Course savedCourse = courses.get(0);
		assertEquals("UpdatedTestCourse", savedCourse.getCourseName());
		Course testCourse2 = new Course(testCourse.getCourseID() + 1, "TestCourse2", "Description2", new HashSet<>(),
				new HashSet<>());
		assertEquals(null, courseService.updateCourse(testCourse2));
	}

}
