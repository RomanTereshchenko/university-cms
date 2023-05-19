package com.foxminded.javaspring.universitycms.controllerTests;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

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

import com.foxminded.javaspring.universitycms.controller.LessonController;
import com.foxminded.javaspring.universitycms.generator.LessonGenerator;
import com.foxminded.javaspring.universitycms.generator.ScheduleGenerator;
import com.foxminded.javaspring.universitycms.model.Course;
import com.foxminded.javaspring.universitycms.model.Group;
import com.foxminded.javaspring.universitycms.model.Lesson;
import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.model.Role;
import com.foxminded.javaspring.universitycms.model.Teacher;
import com.foxminded.javaspring.universitycms.security.MethodSecurityConfig;
import com.foxminded.javaspring.universitycms.security.SecSecurityConfig;
import com.foxminded.javaspring.universitycms.service.CourseService;
import com.foxminded.javaspring.universitycms.service.GroupService;
import com.foxminded.javaspring.universitycms.service.LessonComparator;
import com.foxminded.javaspring.universitycms.service.LessonService;
import com.foxminded.javaspring.universitycms.service.PersonService;
import com.foxminded.javaspring.universitycms.service.TeacherService;

@ExtendWith(SpringExtension.class)
@WebMvcTest({LessonController.class, MethodSecurityConfig.class, SecSecurityConfig.class})
class LessonControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LessonService lessonService;
	
	@MockBean
	private CourseService courseService;
	
	@MockBean
	private PersonService personService;
	
	@MockBean
	private GroupService groupService;
	
	@MockBean
	private TeacherService teacherService;
	
	@MockBean
	private LessonGenerator lessonGenerator;
	
	@MockBean
	private ScheduleGenerator scheduleGenerator;
	
	@MockBean
	private LessonComparator lessonComparator;

	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenLessons_thenStatus200() throws Exception {
		Person userPerson = new Person();
		userPerson.setRole(Role.ADMIN);
		Mockito.when(personService.findPersonByLogin(anyString())).thenReturn(userPerson);
		mockMvc
		.perform(get("/lessons")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("lessons/lessons"))
		.andExpect(status()
		.isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGetAllLessons_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/lessons/all")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("lessons/all"))
		.andExpect(status()
		.isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenLessonsCreate_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/lessons/create")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view().name("lessons/create"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenLessonsCreateSchedule_thenStatus200() throws Exception {
		List<Lesson> lessons = new ArrayList<>();
		Lesson lesson = new Lesson();
		lessons.add(lesson);		
		mockMvc.perform(post("/lessons/create-schedule")
		.contentType(MediaType.APPLICATION_JSON)
		.param("lessonsNumber", "5")
		.param("sessionsNumber", "2")
		.param("startYear", "2023")
		.param("startMonth", "5")
		.param("startDay", "1")
		.param("endYear", "2023")
		.param("endMonth", "5")
		.param("endDay", "5"))
		.andDo(print())
		.andExpect(view().name("lessons/create"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenLessonsAdd_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/lessons/add")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view().name("lessons/add"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenLessonsAddLesson_thenStatus200() throws Exception {	
		Course course = new Course();
		course.setCourseID(0L);
		Teacher teacher = new Teacher();
		teacher.setTeacherID(0L);
		Group group = new Group();
		group.setGroupID(0L);
		courseService.saveNewCourse(course);
		teacherService.saveNewTeacher(teacher);
		groupService.saveNewGroup(group);
		mockMvc.perform(post("/lessons/add-lesson")
		.contentType(MediaType.APPLICATION_JSON)
		.param("lessonYear", "2023")
		.param("lessonMonth", "12")
		.param("lessonDay", "1")
		.param("lessonTime", "9")
		.param("courseId", "0")
		.param("teacherId", "0")
		.param("groupId", "0"))
		.andDo(print())
		.andExpect(view().name("lessons/add"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenLessonsUpdate_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/lessons/update")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("lessons/update"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenLessonsUpdateLesson_thenStatus200() throws Exception {	
		Lesson lesson = new Lesson();
		lesson.setLessonID(0L);
		lessonService.saveNewLesson(lesson);
		Mockito.when(lessonService.findLessonById(anyLong())).thenReturn(lesson);
		mockMvc
		.perform(post("/lessons/update-lesson")
		.contentType(MediaType.APPLICATION_JSON)
		.param("lessonYear", "2024")
		.param("lessonMonth", "6")
		.param("lessonDay", "15")
		.param("lessonTime", "18")
		.param("lessonId", "0"))
		.andDo(print())
		.andExpect(view().name("lessons/update"))
		.andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenLessonsDelete_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/lessons/delete")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("lessons/delete"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenLessonsDeleteLesson_thenStatus200() throws Exception {		
		Lesson lesson = new Lesson();
		lesson.setLessonID(0L);
		lessonService.saveNewLesson(lesson);
		mockMvc
		.perform(post("/lessons/delete-lesson")
		.contentType(MediaType.APPLICATION_JSON)
		.param("lessonId", "0"))
		.andDo(print())
		.andExpect(view().name("lessons/delete"))
		.andExpect(status().isOk());
	}
	
}
