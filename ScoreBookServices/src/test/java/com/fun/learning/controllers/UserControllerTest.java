package com.fun.learning.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fun.learning.model.Gender;
import com.fun.learning.model.User;
import com.fun.learning.service.UserDetailsServiceJDBC;
import com.google.gson.Gson;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserControllerTest {

	@MockBean
	UserDetailsServiceJDBC userDetailService;

	@Autowired
	private MockMvc mockMvc;

	private static Gson gson = new Gson();

	@Test
	public void testUserControllerGet() throws Exception {

		mockMvc.perform(get("/register").accept(MediaType.TEXT_HTML)).andExpect(status().isOk())
				.andExpect(view().name("register")).andExpect(model().attributeExists("user"));

	}

	@Test
	public void testUserControllerPost() throws Exception {
		// set up data
		User postuser = new User.UserBuilder().firstName("Vias").lastName("Chaudhary").password("password1")
				.gender(Gender.Male).username("vikas23").email("vika2453@gmail.com").Build();
		postuser.setConfirmPassword("password1");

		mockMvc.perform(post("/register").param("firstName", "Vikas").param("lastName", "Chaudhary")
				.param("password", "password1").param("confirmPassword", "password1").param("email", "vika2453@gmail.com")
				.param("username", "user").param("gender", "Male"))
				
				.andExpect(status().isOk()).andExpect(view().name("registerSuccessful"));
		
		
		

	}

	@Test
	public void testUserControllerPostMissingParam() throws Exception {

		mockMvc.perform(post("/register").param("firstName", "").param("lastName", "")
				.param("password", "").param("confirmPassword", "").param("email", "")
				.param("username", "").param("gender", ""))
		.andExpect(view().name("register"))
		.andExpect(model().attributeHasFieldErrors("user", "firstName","lastName", "password","email","username","gender"));
	}

	
	@Test
	public void testUserControllerConfirmPassword() throws Exception {

		mockMvc.perform(post("/register").param("firstName", "Vikas").param("lastName", "Chaudhary")
				.param("password", "password1").param("confirmPassword", "password2").param("email", "vika2453@gmail.com")
				.param("username", "user").param("gender", "Male"))
		.andExpect(view().name("register"))
		.andExpect(model().attributeHasErrors("user"));
		
	}

}
