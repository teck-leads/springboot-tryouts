package com.techleads.app.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Random;

import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.techleads.app.SpringBootControllersApplication;

@TestMethodOrder(Alphanumeric.class)
@SpringBootTest(classes = { SpringBootControllersApplication.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class SpringBootControllersApplicationTests {

	@LocalServerPort
	int port;
	RestTemplate template = new RestTemplate();

	public String generateString() {
		Random random = new Random();
		String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		candidateChars.charAt(random.nextInt(candidateChars.length()));
		String randStr = "";
		while (randStr.length() < 8)
			randStr += candidateChars.charAt(random.nextInt(candidateChars.length()));
		return randStr;
	}

	@Test
	public void test1_invalidGet() {
		try {
			boolean didThrow = false;
			try {
				template.getForEntity("http://localhost:" + port + "/users/3", String.class);
			} catch (HttpClientErrorException e) {
				assertEquals(e.getRawStatusCode(), 404);
				didThrow = true;
			}
			assert (didThrow);
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}

	@Test
	public void test2_createUser() {
		try {
			String name1 = generateString(), name2 = generateString();
			ResponseEntity<Integer> id1 = template.postForEntity("http://localhost:" + port + "/users", name1,
					Integer.class);
			ResponseEntity<Integer> id2 = template.postForEntity("http://localhost:" + port + "/users", name2,
					Integer.class);
			assertEquals(id1.getBody().intValue(), 0);
			assertEquals(id2.getBody().intValue(), 1);
			assertEquals(id1.getStatusCode().value(), 201);
			assertEquals(id2.getStatusCode().value(), 201);
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}

	@Test
	public void test3_getAllUsers() {
		try {
			String name3 = generateString(), name4 = generateString();
			template.postForEntity("http://localhost:" + port + "/users", name3, Integer.class);
			template.postForEntity("http://localhost:" + port + "/users", name4, Integer.class);
			ResponseEntity<String[]> allUsers = template.getForEntity("http://localhost:" + port + "/users",
					String[].class);
			assertEquals(allUsers.getBody()[2], name3);
			assertEquals(allUsers.getBody()[3], name4);
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}

	@Test
	public void test4_getSpecificUsers() {
		try {
			String name5 = generateString();
			template.postForEntity("http://localhost:" + port + "/users", name5, Integer.class);
			ResponseEntity<String> user = template.getForEntity("http://localhost:" + port + "/users/4", String.class);
			assertEquals(user.getBody(), name5);
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}

	@Test
	public void test5_updateUser() {
		try {
			String name6 = generateString();
			template.put("http://localhost:" + port + "/users/0", name6);
			ResponseEntity<String> user = template.getForEntity("http://localhost:" + port + "/users/0", String.class);
			assertEquals(user.getBody(), name6);
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}

	@Test
	public void test6_deleteUser() {
		try {
			String[] allUsers1 = template.getForEntity("http://localhost:" + port + "/users", String[].class).getBody();
			template.delete("http://localhost:" + port + "/users/0");
			String[] allUsers2 = template.getForEntity("http://localhost:" + port + "/users", String[].class).getBody();
			assertEquals(allUsers1.length, allUsers2.length + 1);
			assertNotEquals(allUsers1[0], allUsers2[0]);
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}

}
