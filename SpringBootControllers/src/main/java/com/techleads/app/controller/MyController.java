package com.techleads.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techleads.app.service.UsersService;

@RestController
public class MyController {
	@Autowired
	private UsersService usersService;

	@PostMapping(value = { "/users" })
	public ResponseEntity<Integer> createUser(@RequestBody String user) {
		Integer index = usersService.addUser(user);
		return new ResponseEntity<>(index, HttpStatus.CREATED);

	}

	@GetMapping(value = { "/users" })
	public ResponseEntity<List<String>> findAllUsers() {
		List<String> findAllUsers = usersService.findAllUsers();
		return new ResponseEntity<>(findAllUsers, HttpStatus.OK);

	}

	@GetMapping(value = { "/users/{id}" })
	public ResponseEntity<String> findUserById(@PathVariable("id") Integer id) {
		String findUserByIndex = usersService.findUserByIndex(id);
		return new ResponseEntity<>(findUserByIndex, HttpStatus.OK);

	}
	
	@PutMapping(value = { "/users/{id}" })
	public ResponseEntity<String> updateUserById(@PathVariable("id") Integer id,@RequestBody String user) {
		String updateUserById = usersService.updateUserById(id, user);
		return new ResponseEntity<>(updateUserById, HttpStatus.OK);

	}
	
	@DeleteMapping(value = { "/users/{id}" })
	public ResponseEntity<String> deleteUserById(@PathVariable("id") Integer id) {
		 usersService.deleteUserById(id);
		return new ResponseEntity<>("Deleted", HttpStatus.OK);

	}

}