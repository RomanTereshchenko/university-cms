package com.foxminded.javaspring.universitycms.controllerTests;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.javaspring.universitycms.controller.CourseController;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Role;
import com.foxminded.javaspring.universitycms.service.CourseService;
import com.foxminded.javaspring.universitycms.service.GroupService;
import com.foxminded.javaspring.universitycms.service.PersonService;
import com.foxminded.javaspring.universitycms.service.TeacherService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CourseController.class)
class CourseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CourseService courseService;
	
	@MockBean
	private PersonService personService;
	
	@MockBean
	private GroupService groupService;
	
	@MockBean
	private TeacherService teacherService;

	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGetCourses_thenStatus200() throws Exception {
		Person userPerson = new Person();
		userPerson.setRole(Role.ADMIN);
		Mockito.when(personService.findPersonByLogin(anyString())).thenReturn(userPerson);
		mockMvc
		.perform(get("/courses")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("courses/courses"))
		.andExpect(status()
		.isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGetAllCourses_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/courses/all")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("courses/coursesAll"))
		.andExpect(status()
		.isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenCoursesCreate_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/courses/create")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("courses/coursesCreate"))
		.andExpect(status()
		.isOk());
	}
	

	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenCoursesUpdate_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/courses/update")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("courses/coursesUpdate"))
		.andExpect(status()
		.isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ROLE_ADMIN" })
	void whenCoursesCreateCourse_thenStatus200() throws Exception {		
		Map<String,Object> courseParams = new HashMap<>();
		courseParams.put("name", "testName");
		courseParams.put("description", "testDescription");
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc
		.perform(post("/courses/createCourse")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(courseParams)))
		.andDo(print())
		.andExpect(view()
		.name("courses/coursesCreate"))
		.andExpect(status()
		.isOk());
	}

}
