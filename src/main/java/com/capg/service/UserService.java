package com.capg.service;

import org.springframework.http.ResponseEntity;

import com.capg.entity.Users;

public interface UserService {
	public ResponseEntity<?> createUser(Users newUser);

	public Users updateUser(Users newUser);

	public String deleteUser(Integer UserId);

	public Iterable<Users> displayAllUser();

	public ResponseEntity<?> findUserById(Integer userId);
	
	public boolean checkDataExistence(Users newUser);
	
	public Users returnUser(Integer userId);
}
