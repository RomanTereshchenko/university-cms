package com.foxminded.javaspring.universitycms.controllerTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.foxminded.javaspring.universitycms.controller.GroupController;
import com.foxminded.javaspring.universitycms.security.MethodSecurityConfig;
import com.foxminded.javaspring.universitycms.security.SecSecurityConfig;
import com.foxminded.javaspring.universitycms.service.CourseService;
import com.foxminded.javaspring.universitycms.service.GroupService;
import com.foxminded.javaspring.universitycms.service.PersonService;
import com.foxminded.javaspring.universitycms.service.TeacherService;

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
	private TeacherService teacherService;

	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGetGroups_thenStatus200() throws Exception {
		mockMvc
		.perform(get("/groups/all")
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(view()
		.name("groups"))
		.andExpect(status()
		.isOk());
	}

}
