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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.foxminded.javaspring.universitycms.controller.GroupController;
import com.foxminded.javaspring.universitycms.service.GroupService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GroupController.class)
class GroupControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GroupService groupService;

	@Test
	void whenGetGroups_thenStatus200() throws Exception {

		mockMvc.perform(get("/groups/all")
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(view().name("groups"))
			.andExpect(status().isOk());

	}

}
