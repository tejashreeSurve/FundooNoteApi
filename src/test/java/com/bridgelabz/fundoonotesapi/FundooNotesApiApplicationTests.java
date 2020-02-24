package com.bridgelabz.fundoonotesapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
@RunWith(SpringRunner.class)
@SpringBootTest
class FundooNotesApiApplicationTests {
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate testTemplate;
	
	public void getAllUser() throws RestClientException, MalformedURLException {
		ResponseEntity<String> response = testTemplate.getForEntity(new URL("http://localhost:"+port+"/userRegistration/getAllUser").toString(),String.class);
		assertEquals(200, response.getStatusCode());
	}
	@Test
	void contextLoads() {
	}

}
