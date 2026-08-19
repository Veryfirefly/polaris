package com.xsdq.polaris.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsdq.polaris.repository.vo.LoginRequest;
import com.xsdq.polaris.servlet.MockServletTestKit;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AuthControllerTest extends MockServletTestKit {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void login() throws Exception {
		mockMvc.perform(
				post("/api/auth/login")
						.content(loginRequestBody())
						.header("content-type", "application/json")
						.header("user-agent", randomChoiceUserAgent())
				)
				.andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void logout() throws Exception {
		mockMvc.perform(
				post("/api/auth/logout")
						.header("Authorization", "Bearer %s")
				)
				.andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.log());
	}

	@Test
	void accessUnauthorizedResources() throws Exception {

	}

	@Test
	void accessNotFoundResources() throws Exception {

	}

	private String loginRequestBody() throws JsonProcessingException {
		LoginRequest loginRequest = new LoginRequest("xiaoyu", "123456");
		return objectMapper.writeValueAsString(loginRequest);
	}
}
