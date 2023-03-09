package com.foxminded.javaspring.universitycms.controllerTests;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit4.SpringRunner;

import com.foxminded.javaspring.universitycms.model.Course;


public class CourseControllerTest {

	@Test
	public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
	  throws Exception {
	    
	    Course course = new Course();

	    List<Course> allCpurses = Arrays.asList(course);

	    given(service.getAllEmployees()).willReturn(allEmployees);

	    mvc.perform(get("/api/employees")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$", hasSize(1)))
	      .andExpect(jsonPath("$[0].name", is(alex.getName())));
	}
	
}
