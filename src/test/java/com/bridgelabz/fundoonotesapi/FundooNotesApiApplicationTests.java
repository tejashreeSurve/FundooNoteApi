package com.bridgelabz.fundoonotesapi;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bridgelabz.fundoonotesapi.model.UserEntity;
import com.bridgelabz.fundoonotesapi.response.Response;
import com.bridgelabz.fundoonotesapi.services.UserServiceImp;
@RunWith(SpringRunner.class)
@SpringBootTest
class FundooNotesApiApplicationTests {
	@Autowired
	private MockMvc mvc;
	@MockBean
	private UserServiceImp userServiceImp;
	
	@Test
	public void findAll() throws Exception {
		UserEntity user = new UserEntity();
		user.setId(1);
		user.setCity("mumbai");
		user.setEmail("tejutanvi773@gmail.com");
		user.setFirstName("Tejashree");
		user.setLastName("surve");
		user.setIsValidate(false);
		user.setPhoneNumber("4454644625");
		user.setUserPassword("teju123");
		List<UserEntity> userlist = Arrays.asList(user);
		when(userServiceImp.getAllUser()).thenReturn((Response) userlist);
		mvc.perform(get("/userRegistration/getAllUser")).andExpect(status().isOk()).
		andExpect(jsonPath("$[0].id", is(1))).
		andExpect(jsonPath("$[0].city", is("mumbai"))).
		andExpect(jsonPath("$[0].birthdate", is("21/01/1998"))).
		andExpect(jsonPath("$[0].email", is("tejutanvi773@gmail.com"))).
		andExpect(jsonPath("$[0].firstname", is("Tejashree"))).
		andExpect(jsonPath("$[0].lastname", is("surve"))).
		andExpect(jsonPath("$[0].isValidate", is(false))).
		andExpect(jsonPath("$[0].phonenumber", is("4454644625"))).
		andExpect(jsonPath("$[0].userpassword", is("teju123")));
		verify(userServiceImp, times(1)).getAllUser();
        verifyNoMoreInteractions(userServiceImp);
	}
	}
