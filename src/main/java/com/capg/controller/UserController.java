package com.capg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.entity.Users;
import com.capg.exceptions.RecordAlreadyPresentException;
import com.capg.exceptions.RecordNotFoundException;
import com.capg.service.UserService;

@ComponentScan(basePackages = "com.capg")
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;

	@PostMapping("/createUser")
	@ExceptionHandler(RecordAlreadyPresentException.class)
	public ResponseEntity<?> addUser(@RequestBody Users newUser) {
		boolean isDataExist = userService.checkDataExistence(newUser);
		if (isDataExist) {
			String msg = "User with ID: "+newUser.getUserId()+" has already registered!";
			return new ResponseEntity<>(msg, HttpStatus.ALREADY_REPORTED); 
		}
		else {
			userService.createUser(newUser);
			String msg = "Created User "+newUser.getUserName();
			return new ResponseEntity<>(msg, HttpStatus.ACCEPTED); 
		}
	}

	@GetMapping("/readAllUsers")
	public Iterable<Users> readAllUsers() {

		return userService.displayAllUser();
	}

	@PutMapping("/updateUser")
	@ExceptionHandler(RecordNotFoundException.class)
	public void updateUser(@RequestBody Users updateUser) {
		
		userService.updateUser(updateUser);
	}
 
	@GetMapping("/searchUser/{id}")
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<?> searchUserByID(@PathVariable("id") Integer userId) {

		return userService.findUserById(userId);
	}

	@DeleteMapping("/deleteUser/{id}")
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<?> deleteBookingByID(@PathVariable("id") Integer userId) {
		String msg = userService.deleteUser(userId);
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
}
