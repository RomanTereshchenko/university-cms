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

import com.foxminded.javaspring.universitycms.dao.CourseDao;
import com.foxminded.javaspring.universitycms.model.Course;
import com.foxminded.javaspring.universitycms.service.CourseService;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
	
	@Mock
	private CourseDao courseDao;
	
	@InjectMocks
	private CourseService courseService;
	
	@Test
	void testSaveNewCourse() throws SQLException {
		Course course = new Course();
		course.setCourseName("TestCourse");
		Mockito.when(courseDao.save(any(Course.class))).thenReturn(course);
		Course savedCourse = courseService.saveNewCourse(course);
		assertEquals("TestCourse", savedCourse.getCourseName());
	}
	
	@Test
	void testFindCourseById() throws SQLException {
		Course savedCourse = new Course();
		savedCourse.setCourseID(1L);
		Mockito.when(courseDao.findById(savedCourse .getCourseID())).thenReturn(Optional.of(savedCourse ));
		Course foundCourse = courseService.findCourseById(savedCourse.getCourseID());
		assertEquals(1L, foundCourse.getCourseID());
		Course notFoundCourse = courseService.findCourseById(0L);
		assertEquals(null, notFoundCourse);
	}
	
	@Test
	void testFindAllCourses() throws SQLException {
		List<Course> allCoursesList = new ArrayList<>();
		Course course = new Course();
		course.setCourseName("TestCourse");
		allCoursesList.add(course);
		Mockito.when(courseDao.findAll()).thenReturn(allCoursesList);
		List<Course> foundCourses = courseService.findAllCourses();
		assertEquals("TestCourse", foundCourses.get(0).getCourseName());
	}
	
	@Test
	void testUpdateCourse() throws SQLException {
		Course dBCourse = new Course();
		dBCourse.setCourseID(1L);
		Course updatingCourse = new Course();
		updatingCourse.setCourseID(dBCourse.getCourseID());
		updatingCourse.setCourseName("UpdatingCourse");
		Course nonUpdatingCourse = new Course();
		nonUpdatingCourse.setCourseID(0L);
		Mockito.when(courseDao.findById(dBCourse.getCourseID())).thenReturn(Optional.of(dBCourse));
		Course updatedCourse = courseService.updateCourse(updatingCourse);
		assertEquals("UpdatingCourse", updatedCourse.getCourseName());
		Course nonUpdatedCourse = courseService.updateCourse(nonUpdatingCourse);
		assertEquals(null, nonUpdatedCourse);
	}
	
	@Test
	void testDeleteCourse() throws SQLException {
		courseService.deleteCourseById(anyLong());
		Mockito.verify(courseDao).deleteById(anyLong());
	}

}
