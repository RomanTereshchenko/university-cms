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

import com.foxminded.javaspring.universitycms.controller.GroupController;
import com.foxminded.javaspring.universitycms.model.Course;
import com.foxminded.javaspring.universitycms.model.Group;
import com.foxminded.javaspring.universitycms.model.Lesson;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Role;
import com.foxminded.javaspring.universitycms.model.Student;
import com.foxminded.javaspring.universitycms.security.MethodSecurityConfig;
import com.foxminded.javaspring.universitycms.security.SecSecurityConfig;
import com.foxminded.javaspring.universitycms.service.CourseService;
import com.foxminded.javaspring.universitycms.service.GroupService;
import com.foxminded.javaspring.universitycms.service.PersonService;
import com.foxminded.javaspring.universitycms.service.StudentService;

@ExtendWith(SpringExtension.class)
@WebMvcTest({GroupController.class, MethodSecurityConfig.class, SecSecurityConfig.class})
class GroupControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CourseService courseService;
	
	@MockBean
	private PersonService personService;
	
	@MockBean
	private GroupService groupService;
	
	@MockBean
	private StudentService studentService;

	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGetGroups_thenStatus200() throws Exception {
		Person userPerson = new Person();
		userPerson.setRole(Role.ADMIN);
		Mockito.when(personService.findPersonByLogin(anyString())).thenReturn(userPerson);
		mockMvc
		.perform(get("/groups")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("groups/groups"))
		.andExpect(status()
		.isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGetAllGroups_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/groups/all")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("groups/groupsAll"))
		.andExpect(status()
		.isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGroupsCreate_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/groups/create")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view().name("groups/groupsCreate"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGroupsCreateGroup_thenStatus200() throws Exception {		
		Map<String,Object> groupParams = new HashMap<>();
		groupParams.put("name", "testName");
		mockMvc.perform(post("/groups/createGroup")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view().name("groups/groupsCreate"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGroupsUpdate_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/groups/update")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("groups/groupsUpdate"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGroupsUpdateGroup_thenStatus200() throws Exception {	
		Group group = new Group();
		group.setGroupID(0L);
		group.setGroupName("testName");
		group.setCourses(new HashSet<Course>());
		group.setStudents(new HashSet<Student>());
		group.setLessons(new HashSet<Lesson>());
		groupService.saveNewGroup(group);
		Mockito.when(groupService.findGroupById(anyLong())).thenReturn(group);
		mockMvc
		.perform(post("/groups/updateGroup")
		.contentType(MediaType.APPLICATION_JSON)
		.param("groupId", "0"))
		.andDo(print())
		.andExpect(view().name("groups/groupsUpdate"))
		.andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGroupsDelete_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/groups/delete")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("groups/groupsDelete"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGroupsDeleteGroup_thenStatus200() throws Exception {		
		Group group = new Group();
		group.setGroupID(0L);
		group.setGroupName("testName");
		group.setCourses(new HashSet<Course>());
		group.setStudents(new HashSet<Student>());
		group.setLessons(new HashSet<Lesson>());
		groupService.saveNewGroup(group);
		mockMvc
		.perform(post("/groups/deleteGroup")
		.contentType(MediaType.APPLICATION_JSON)
		.param("groupId", "0"))
		.andDo(print())
		.andExpect(view().name("groups/groupsDelete"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGroupsAssignCourse_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/groups/assignCourse")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("groups/groupsAssignCourse"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGroupsAssignCourseToGroup_thenStatus200() throws Exception {
		Group group = new Group();
		group.setGroupID(0L);
		group.setGroupName("testName");
		group.setCourses(new HashSet<Course>());
		group.setStudents(new HashSet<Student>());
		group.setLessons(new HashSet<Lesson>());
		Course course = new Course();
		Mockito.when(groupService.findGroupById(anyLong())).thenReturn(group);
		Mockito.when(courseService.findCourseById(anyLong())).thenReturn(course);
		mockMvc
		.perform(post("/groups/assignCourseToGroup")
		.contentType(MediaType.APPLICATION_JSON)
		.param("groupId", "0")
		.param("assignedCourseId", "0"))
		.andDo(print())
		.andExpect(view()
		.name("groups/groupsAssignCourse"))
		.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGroupsUnassignCourseToGroup_thenStatus200() throws Exception {
		Group group = new Group();
		group.setGroupID(0L);
		group.setGroupName("testName");
		group.setCourses(new HashSet<Course>());
		group.setStudents(new HashSet<Student>());
		group.setLessons(new HashSet<Lesson>());
		Course course = new Course();
		Mockito.when(groupService.findGroupById(anyLong())).thenReturn(group);
		Mockito.when(courseService.findCourseById(anyLong())).thenReturn(course);
		mockMvc
		.perform(post("/groups/unassignCourseToGroup")
		.contentType(MediaType.APPLICATION_JSON)
		.param("groupId", "0")
		.param("unassignedCourseId", "0"))
		.andDo(print())
		.andExpect(view()
		.name("groups/groupsAssignCourse"))
		.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGroupsAssignStudent_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/groups/assignStudent")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("groups/groupsAssignStudent"))
		.andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGroupsAssignTeacherToGroup_thenStatus200() throws Exception {
		Group group = new Group();
		group.setGroupID(0L);
		group.setGroupName("testName");
		group.setCourses(new HashSet<Course>());
		group.setStudents(new HashSet<Student>());
		group.setLessons(new HashSet<Lesson>());
		Student student = new Student();
		Mockito.when(groupService.findGroupById(anyLong())).thenReturn(group);
		Mockito.when(studentService.findStudentById(anyLong())).thenReturn(student);
		mockMvc
		.perform(post("/groups/assignStudentToGroup")
		.contentType(MediaType.APPLICATION_JSON)
		.param("groupId", "0")
		.param("assignedStudentId", "0"))
		.andDo(print())
		.andExpect(view()
		.name("groups/groupsAssignStudent"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGroupsUnassignStudentToGroup_thenStatus200() throws Exception {
		Group group = new Group();
		group.setGroupID(0L);
		group.setGroupName("testName");
		group.setCourses(new HashSet<Course>());
		group.setStudents(new HashSet<Student>());
		group.setLessons(new HashSet<Lesson>());
		Student student = new Student();
		Mockito.when(groupService.findGroupById(anyLong())).thenReturn(group);
		Mockito.when(studentService.findStudentById(anyLong())).thenReturn(student);
		mockMvc
		.perform(post("/groups/unassignStudentToGroup")
		.contentType(MediaType.APPLICATION_JSON)
		.param("groupId", "0")
		.param("unassignedStudentId", "0"))
		.andDo(print())
		.andExpect(view()
		.name("groups/groupsAssignStudent"))
		.andExpect(status().isOk());
	}

}
