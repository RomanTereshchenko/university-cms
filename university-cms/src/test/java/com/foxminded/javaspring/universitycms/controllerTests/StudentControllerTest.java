package com.foxminded.javaspring.universitycms.controllerTests;

import static org.mockito.ArgumentMatchers.anyLong;
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

import com.foxminded.javaspring.universitycms.controller.StudentController;
import com.foxminded.javaspring.universitycms.model.Group;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Role;
import com.foxminded.javaspring.universitycms.model.Student;
import com.foxminded.javaspring.universitycms.security.MethodSecurityConfig;
import com.foxminded.javaspring.universitycms.security.SecSecurityConfig;
import com.foxminded.javaspring.universitycms.service.GroupService;
import com.foxminded.javaspring.universitycms.service.PersonService;
import com.foxminded.javaspring.universitycms.service.StudentService;

@ExtendWith(SpringExtension.class)
@WebMvcTest({StudentController.class, MethodSecurityConfig.class, SecSecurityConfig.class})
class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService studentService;
	
	@MockBean
	private PersonService personService;
	
	@MockBean
	private GroupService groupService;

	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGetStudents_thenStatus200() throws Exception {
		Person userPerson = new Person();
		userPerson.setRole(Role.ADMIN);
		Mockito.when(personService.findPersonByLogin(anyString())).thenReturn(userPerson);
		mockMvc
		.perform(get("/students")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("students/students"))
		.andExpect(status()
		.isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGetAllStudents_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/students/all")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("students/all"))
		.andExpect(status()
		.isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenStudentsCreate_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/students/create")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view().name("students/create"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenStudentsCreateStudent_thenStatus200() throws Exception {		
		Map<String,Object> studentParams = new HashMap<>();
		studentParams.put("firstName", "testFirstName");
		studentParams.put("lastName", "testLastName");
		studentParams.put("login", "testLogin");
		studentParams.put("password", "testPassword");
		mockMvc.perform(post("/students/create-student")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view().name("students/create"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenStudentsUpdate_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/students/update")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("students/update"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenStudentsUpdateStudent_thenStatus200() throws Exception {	
		Student student = new Student();
		Person person = new Person();
		student.setStudentID(0L);
		person.setPersonID(0L);
		person.setFirstName("testFirstName");
		person.setLastName("testLastName");
		person.setLogin("testLogin");
		person.setPassword("testPassword");
		person.setRole(Role.STUDENT);
		student.setPerson(person);
		personService.saveNewPerson(person);
		studentService.saveNewStudent(student);
		Mockito.when(personService.findPersonById(anyLong())).thenReturn(person);
		Mockito.when(studentService.findStudentById(anyLong())).thenReturn(student);
		mockMvc
		.perform(post("/students/update-student")
		.contentType(MediaType.APPLICATION_JSON)
		.param("studentId", "0"))
		.andDo(print())
		.andExpect(view().name("students/update"))
		.andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenStudentsDelete_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/students/delete")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("students/delete"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenStudentsDeleteStudent_thenStatus200() throws Exception {		
		Student student = new Student();
		Person person = new Person();
		student.setStudentID(0L);
		person.setFirstName("testFirstName");
		person.setLastName("testLastName");
		person.setLogin("testLogin");
		person.setPassword("testPassword");
		person.setRole(Role.STUDENT);
		student.setPerson(person);
		studentService.saveNewStudent(student);
		mockMvc
		.perform(post("/students/delete-student")
		.contentType(MediaType.APPLICATION_JSON)
		.param("studentId", "0"))
		.andDo(print())
		.andExpect(view().name("students/delete"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenStudentsAssignGroup_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/students/assign-group")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("students/assign-group"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenStudentsAssignGroupToStudent_thenStatus200() throws Exception {
		Student student = new Student();
		Person person = new Person();
		student.setStudentID(0L);
		person.setFirstName("testFirstName");
		person.setLastName("testLastName");
		person.setLogin("testLogin");
		person.setPassword("testPassword");
		person.setRole(Role.STUDENT);
		student.setPerson(person);
		Group group = new Group();
		Mockito.when(studentService.findStudentById(anyLong())).thenReturn(student);
		Mockito.when(groupService.findGroupById(anyLong())).thenReturn(group);
		mockMvc
		.perform(post("/students/assign-group-to-student")
		.contentType(MediaType.APPLICATION_JSON)
		.param("studentId", "0")
		.param("assignedGroupId", "0"))
		.andDo(print())
		.andExpect(view()
		.name("students/assign-group"))
		.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenStudentsUnassignGroupToStudent_thenStatus200() throws Exception {
		Student student = new Student();
		Person person = new Person();
		student.setStudentID(0L);
		person.setFirstName("testFirstName");
		person.setLastName("testLastName");
		person.setLogin("testLogin");
		person.setPassword("testPassword");
		person.setRole(Role.STUDENT);
		student.setPerson(person);
		Group group = new Group();
		Mockito.when(studentService.findStudentById(anyLong())).thenReturn(student);
		Mockito.when(groupService.findGroupById(anyLong())).thenReturn(group);
		mockMvc
		.perform(post("/students/unassign-group-from-student")
		.contentType(MediaType.APPLICATION_JSON)
		.param("studentId", "0")
		.param("unassignedGroupId", "0"))
		.andDo(print())
		.andExpect(view()
		.name("students/assign-group"))
		.andExpect(status().isOk());
	}

}
