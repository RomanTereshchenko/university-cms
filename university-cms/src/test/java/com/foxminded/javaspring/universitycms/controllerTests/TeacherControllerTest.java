package com.foxminded.javaspring.universitycms.controllerTests;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

import com.foxminded.javaspring.universitycms.controller.TeacherController;
import com.foxminded.javaspring.universitycms.model.Course;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Role;
import com.foxminded.javaspring.universitycms.model.Teacher;
import com.foxminded.javaspring.universitycms.security.MethodSecurityConfig;
import com.foxminded.javaspring.universitycms.security.SecSecurityConfig;
import com.foxminded.javaspring.universitycms.service.CourseService;
import com.foxminded.javaspring.universitycms.service.PersonService;
import com.foxminded.javaspring.universitycms.service.TeacherService;

@ExtendWith(SpringExtension.class)
@WebMvcTest({TeacherController.class, MethodSecurityConfig.class, SecSecurityConfig.class})
class TeacherControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TeacherService teacherService;
	
	@MockBean
	private PersonService personService;
	
	@MockBean
	private CourseService courseService;

	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGetTeachers_thenStatus200() throws Exception {
		Person userPerson = new Person();
		userPerson.setRole(Role.ADMIN);
		Mockito.when(personService.findPersonByLogin(anyString())).thenReturn(userPerson);
		mockMvc
		.perform(get("/teachers")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("teachers/teachers"))
		.andExpect(status()
		.isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGetAllTeachers_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/teachers/all")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("teachers/all"))
		.andExpect(status()
		.isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenTeachersCreate_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/teachers/create")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view().name("teachers/create"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenTeachersCreateTeacher_thenStatus200() throws Exception {		
		Map<String,Object> teacherParams = new HashMap<>();
		teacherParams.put("firstName", "testFirstName");
		teacherParams.put("lastName", "testLastName");
		teacherParams.put("login", "testLogin");
		teacherParams.put("password", "testPassword");
		mockMvc.perform(post("/teachers/create-teacher")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view().name("teachers/create"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenTeachersUpdate_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/teachers/update")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("teachers/update"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenTeachersUpdateTeacher_thenStatus200() throws Exception {	
		Teacher teacher = new Teacher();
		Person person = new Person();
		teacher.setTeacherID(0L);
		person.setPersonID(0L);
		person.setFirstName("testFirstName");
		person.setLastName("testLastName");
		person.setLogin("testLogin");
		person.setPassword("testPassword");
		person.setRole(Role.TEACHER);
		teacher.setPerson(person);
		personService.saveNewPerson(person);
		teacherService.saveNewTeacher(teacher);
		Mockito.when(personService.findPersonById(anyLong())).thenReturn(person);
		Mockito.when(teacherService.findTeacherById(anyLong())).thenReturn(teacher);
		mockMvc
		.perform(post("/teachers/update-teacher")
		.contentType(MediaType.APPLICATION_JSON)
		.param("teacherId", "0"))
		.andDo(print())
		.andExpect(view().name("teachers/update"))
		.andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenTeachersDelete_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/teachers/delete")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("teachers/delete"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenTeachersDeleteTeacher_thenStatus200() throws Exception {		
		Teacher teacher = new Teacher();
		Person person = new Person();
		teacher.setTeacherID(0L);
		person.setFirstName("testFirstName");
		person.setLastName("testLastName");
		person.setLogin("testLogin");
		person.setPassword("testPassword");
		person.setRole(Role.TEACHER);
		teacher.setPerson(person);
		teacherService.saveNewTeacher(teacher);
		mockMvc
		.perform(post("/teachers/delete-teacher")
		.contentType(MediaType.APPLICATION_JSON)
		.param("teacherId", "0"))
		.andDo(print())
		.andExpect(view().name("teachers/delete"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenTeachersAssignCourse_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/teachers/assign-course")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("teachers/assign-course"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenTeachersAssignCourseToTeacher_thenStatus200() throws Exception {
		Teacher teacher = new Teacher();
		Person person = new Person();
		teacher.setTeacherID(0L);
		person.setFirstName("testFirstName");
		person.setLastName("testLastName");
		person.setLogin("testLogin");
		person.setPassword("testPassword");
		person.setRole(Role.TEACHER);
		teacher.setPerson(person);
		Set<Course> courses = new HashSet<>();
		Course course = new Course();
		course.setCourseID(0L);
		courses.add(course);
		teacher.setCourses(courses);
		Mockito.when(teacherService.findTeacherById(anyLong())).thenReturn(teacher);
		Mockito.when(courseService.findCourseById(anyLong())).thenReturn(course);
		mockMvc
		.perform(post("/teachers/assign-course-to-teacher")
		.contentType(MediaType.APPLICATION_JSON)
		.param("teacherId", "0")
		.param("assignedCourseId", "0"))
		.andDo(print())
		.andExpect(view()
		.name("teachers/assign-course"))
		.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenTeachersUnassignCourseFromTeacher_thenStatus200() throws Exception {
		Teacher teacher = new Teacher();
		Person person = new Person();
		teacher.setTeacherID(0L);
		person.setFirstName("testFirstName");
		person.setLastName("testLastName");
		person.setLogin("testLogin");
		person.setPassword("testPassword");
		person.setRole(Role.TEACHER);
		teacher.setPerson(person);
		Set<Course> courses = new HashSet<>();
		Course course = new Course();
		course.setCourseID(0L);
		courses.add(course);
		teacher.setCourses(courses);
		Mockito.when(teacherService.findTeacherById(anyLong())).thenReturn(teacher);
		Mockito.when(courseService.findCourseById(anyLong())).thenReturn(course);
		mockMvc
		.perform(post("/teachers/unassign-course-from-teacher")
		.contentType(MediaType.APPLICATION_JSON)
		.param("teacherId", "0")
		.param("unassignedCourseId", "0"))
		.andDo(print())
		.andExpect(view()
		.name("teachers/assign-course"))
		.andExpect(status().isOk());
	}

}
