package com.xsdq.polaris.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsdq.polaris.repository.dao.UserDao;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PolarisUserDetailsTest {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testUserDetailsSerializable() throws JsonProcessingException {
		var user = userDao.selectById(1L);
		assertNotNull(user);

		var userDetails = new PolarisUserDetails(user);
		var strJsonUserDetails = objectMapper.writeValueAsString(userDetails);
		assertTrue(strJsonUserDetails.startsWith("{"));

		// Deserialize objects using the constructor.
		var deserializeUserDetails = assertDoesNotThrow(() -> objectMapper.readValue(strJsonUserDetails, PolarisUserDetails.class));
		assertEquals(userDetails, deserializeUserDetails);

		assertEquals(userDetails.getUsername(), deserializeUserDetails.getUsername());
		assertEquals(userDetails.getPassword(), deserializeUserDetails.getPassword());
	}
}
