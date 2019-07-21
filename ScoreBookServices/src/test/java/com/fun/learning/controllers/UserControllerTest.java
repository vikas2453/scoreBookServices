package com.fun.learning.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fun.learning.model.Gender;
import com.fun.learning.model.User;
import com.fun.learning.service.UserDetailsServiceJDBC;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserControllerTest {

	@MockBean
	UserDetailsServiceJDBC userDetailService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testUserController() throws Exception {
		// set up data
		User postuser = new User.UserBuilder().firstName("Vikas").lastName("Chaudhary").password("password1")
				.gender(Gender.Male).username("vikas2453").email("vikas2453@gmail.com").Build();

		mockMvc.perform(get("/register")).andExpect(status().isOk());

	}

}
