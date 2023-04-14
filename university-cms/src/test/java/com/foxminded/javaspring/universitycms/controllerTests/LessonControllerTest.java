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

import com.foxminded.javaspring.universitycms.controller.LessonController;
import com.foxminded.javaspring.universitycms.service.LessonService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LessonController.class)
class LessonControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LessonService lessonService;

	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	void whenGetLessons_thenStatus200() throws Exception {
		mockMvc.perform(get("/lessons/all").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(view().name("lessons")).andExpect(status().isOk());
	}

}
