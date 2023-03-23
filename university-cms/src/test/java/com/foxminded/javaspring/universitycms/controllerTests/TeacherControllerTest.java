package com.foxminded.javaspring.universitycms.controllerTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.foxminded.javaspring.universitycms.controller.CourseController;
import com.foxminded.javaspring.universitycms.service.CourseService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CourseController.class)
class TeacherControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CourseService courseService;

	@Test
	void whenGetCourses_thenStatus200() throws Exception {

		mockMvc.perform(get("/courses/all")
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(view().name("courses"))
			.andExpect(status().isOk());

	}

}
