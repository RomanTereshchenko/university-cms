package com.foxminded.javaspring.universitycms.controllerTests;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;

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

import com.foxminded.javaspring.universitycms.controller.CourseController;
import com.foxminded.javaspring.universitycms.model.Course;
import com.foxminded.javaspring.universitycms.model.Group;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Role;
import com.foxminded.javaspring.universitycms.model.Teacher;
import com.foxminded.javaspring.universitycms.security.MethodSecurityConfig;
import com.foxminded.javaspring.universitycms.security.SecSecurityConfig;
import com.foxminded.javaspring.universitycms.service.CourseService;
import com.foxminded.javaspring.universitycms.service.GroupService;
import com.foxminded.javaspring.universitycms.service.PersonService;
import com.foxminded.javaspring.universitycms.service.TeacherService;

@ExtendWith(SpringExtension.class)
@WebMvcTest({CourseController.class, MethodSecurityConfig.class, SecSecurityConfig.class})
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
		.name("courses/all"))
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
		.andExpect(view().name("courses/create"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenCoursesCreateCourse_thenStatus200() throws Exception {		
		mockMvc.perform(post("/courses/create-course")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view().name("courses/create"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenCoursesUpdate_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/courses/update")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("courses/update"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenCoursesUpdateCourse_thenStatus200() throws Exception {	
		Course course = new Course();
		course.setCourseID(0L);
		course.setCourseName("testName");
		course.setCourseDescription("testDescription");
		course.setGroups(new HashSet<Group>());
		course.setTeachers(new HashSet<Teacher>());
		courseService.saveNewCourse(course);
		Mockito.when(courseService.findCourseById(anyLong())).thenReturn(course);
		mockMvc
		.perform(post("/courses/update-course")
		.contentType(MediaType.APPLICATION_JSON)
		.param("courseId", "0"))
		.andDo(print())
		.andExpect(view().name("courses/update"))
		.andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenCoursesDelete_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/courses/delete")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("courses/delete"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenCoursesDeleteCourse_thenStatus200() throws Exception {		
		Course course = new Course();
		course.setCourseID(0L);
		course.setCourseName("testName");
		course.setCourseDescription("testDescription");
		course.setGroups(new HashSet<Group>());
		course.setTeachers(new HashSet<Teacher>());
		courseService.saveNewCourse(course);
		mockMvc
		.perform(post("/courses/delete-course")
		.contentType(MediaType.APPLICATION_JSON)
		.param("courseId", "0"))
		.andDo(print())
		.andExpect(view().name("courses/delete"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenCoursesAssignGroup_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/courses/assign-group")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("courses/assign-group"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenCoursesAssignGroupToCourse_thenStatus200() throws Exception {
		Course course = new Course();
		course.setCourseID(0L);
		course.setCourseName("testName");
		course.setCourseDescription("testDescription");
		course.setGroups(new HashSet<Group>());
		course.setTeachers(new HashSet<Teacher>());
		Group group = new Group();
		Mockito.when(courseService.findCourseById(anyLong())).thenReturn(course);
		Mockito.when(groupService.findGroupById(anyLong())).thenReturn(group);
		mockMvc
		.perform(post("/courses/assign-group-to-course")
		.contentType(MediaType.APPLICATION_JSON)
		.param("courseId", "0")
		.param("assignedGroupId", "0"))
		.andDo(print())
		.andExpect(view()
		.name("courses/assign-group"))
		.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenCoursesUnassignGroupToCourse_thenStatus200() throws Exception {
		Course course = new Course();
		course.setCourseID(0L);
		course.setCourseName("testName");
		course.setCourseDescription("testDescription");
		course.setGroups(new HashSet<Group>());
		course.setTeachers(new HashSet<Teacher>());
		Group group = new Group();
		Mockito.when(courseService.findCourseById(anyLong())).thenReturn(course);
		Mockito.when(groupService.findGroupById(anyLong())).thenReturn(group);
		mockMvc
		.perform(post("/courses/unassign-group-from-course")
		.contentType(MediaType.APPLICATION_JSON)
		.param("courseId", "0")
		.param("unassignedGroupId", "0"))
		.andDo(print())
		.andExpect(view()
		.name("courses/assign-group"))
		.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenCoursesAssignTeacher_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/courses/assign-teacher")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("courses/assign-teacher"))
		.andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenCoursesAssignTeacherToCourse_thenStatus200() throws Exception {
		Course course = new Course();
		course.setCourseID(0L);
		course.setCourseName("testName");
		course.setCourseDescription("testDescription");
		course.setGroups(new HashSet<Group>());
		course.setTeachers(new HashSet<Teacher>());
		Teacher teacher = new Teacher();
		Mockito.when(courseService.findCourseById(anyLong())).thenReturn(course);
		Mockito.when(teacherService.findTeacherById(anyLong())).thenReturn(teacher);
		mockMvc
		.perform(post("/courses/assign-teacher-to-course")
		.contentType(MediaType.APPLICATION_JSON)
		.param("courseId", "0")
		.param("assignedTeacherId", "0"))
		.andDo(print())
		.andExpect(view()
		.name("courses/assign-teacher"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenCoursesUnassignTeacherToCourse_thenStatus200() throws Exception {
		Course course = new Course();
		course.setCourseID(0L);
		course.setCourseName("testName");
		course.setCourseDescription("testDescription");
		course.setGroups(new HashSet<Group>());
		course.setTeachers(new HashSet<Teacher>());
		Teacher teacher = new Teacher();
		Mockito.when(courseService.findCourseById(anyLong())).thenReturn(course);
		Mockito.when(teacherService.findTeacherById(anyLong())).thenReturn(teacher);
		mockMvc
		.perform(post("/courses/unassign-teacher-from-course")
		.contentType(MediaType.APPLICATION_JSON)
		.param("courseId", "0")
		.param("unassignedTeacherId", "0"))
		.andDo(print())
		.andExpect(view()
		.name("courses/assign-teacher"))
		.andExpect(status().isOk());
	}
	
}
