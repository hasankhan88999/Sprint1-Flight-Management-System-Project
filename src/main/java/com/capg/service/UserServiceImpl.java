package com.capg.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.capg.dao.UserDao;
import com.capg.entity.Users;
import com.capg.exceptions.RecordAlreadyPresentException;
import com.capg.exceptions.RecordNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Override
	public ResponseEntity<?> createUser(Users newUser) {
		// TODO Auto-generated method stub
		Optional<Users> findUserById = userDao.findById(newUser.getUserId());
		try {
			if (!findUserById.isPresent()) {
				int val = new Random().nextInt(1050, 789343);
				newUser.setUserId(val);
				userDao.save(newUser);
				return new ResponseEntity<Users>(newUser, HttpStatus.OK);
			} else
				throw new RecordAlreadyPresentException(
						"User with Id: " + newUser.getUserId() + " already exists!!");
		} catch (RecordAlreadyPresentException e) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
 
	@Override
	public Users updateUser(Users updateUser) {
		// TODO Auto-generated method stub
		Optional<Users> findUserById = userDao.findById(updateUser.getUserId());
		if (findUserById.isPresent()) {
			userDao.save(updateUser);
		} else
			throw new RecordNotFoundException(
					"User with Id: " + updateUser.getUserId() + " not exists!!");
		return updateUser;
	}

	@Override
	public String deleteUser(Integer UserId) {
		// TODO Auto-generated method stub
		Optional<Users> findBookingById = userDao.findById(UserId);
		if (findBookingById.isPresent()) {
			userDao.deleteById(UserId);
			return "User Deleted!!";
		} else
			throw new RecordNotFoundException("User not found for the entered UserID");
	}

	@Override
	public Iterable<Users> displayAllUser() {
		// TODO Auto-generated method stub
		return userDao.findAll();
	}

	@Override
	public ResponseEntity<?> findUserById(Integer userId) {
		// TODO Auto-generated method stub
		Optional<Users> findById = userDao.findById(userId);
		try {
			if (findById.isPresent()) {
				Users findUser = findById.get();
				return new ResponseEntity<Users>(findUser, HttpStatus.OK);
			} else
				throw new RecordNotFoundException("No record found with ID " + userId);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@Override
	public boolean checkDataExistence(Users newUser) {
		Optional<Users> user =  userDao.findById(newUser.getUserId());
		if(user.isPresent()) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public Users returnUser(Integer userId) {
		Optional<Users> user =  userDao.findById(userId);
		if (user.isPresent()) {
			return user.get();
		} else
			throw new RecordNotFoundException(
					"User with Id: " + user.get().getUserId() + " doesn't exists!!");
	
	}
	
}

