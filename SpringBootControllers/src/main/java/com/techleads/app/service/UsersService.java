package com.techleads.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UsersService {
	private List<String> users =new ArrayList<>();
	
	
	public List<String> findAllUsers(){
		return users;
	}
	
	public String findUserByIndex(Integer id){
		return users.get(id);
	}
	
	public Integer addUser(String name){
		users.add(name);
		int index = users.indexOf(name);
		return index;
	}
	
	public String updateUserById(Integer id, String user){
		users.add(id, user);
		String updatedUser = findUserByIndex(id);
		return updatedUser;
	}
	
	public void deleteUserById(int id){
		users.remove(id);
	}
	
	
}
