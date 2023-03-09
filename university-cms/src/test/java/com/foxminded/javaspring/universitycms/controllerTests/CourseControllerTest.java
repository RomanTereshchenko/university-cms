package com.foxminded.javaspring.universitycms.controllerTests;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.foxminded.javaspring.universitycms.controller.CourseController;
import com.foxminded.javaspring.universitycms.model.Course;
import com.foxminded.javaspring.universitycms.service.CourseService;

@RunWith(SpringRunner.class)
@WebMvcTest(CourseController.class)
public class CourseControllerTest {
	
	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private CourseService courseService;

	@Test
	public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
	  throws Exception {
	    
	    Course course = new Course();

	    List<Course> allCourses = Arrays.asList(course);

	    given(courseService.findAllCourses()).willReturn(allCourses);

	    mvc.perform(get("/api/courses")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$", hasSize(1)))
	      .andExpect(jsonPath("$[0].name", is(course.getCourseName())));
	}
	
}
