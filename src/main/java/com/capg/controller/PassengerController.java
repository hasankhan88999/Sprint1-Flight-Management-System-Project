package com.capg.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.capg.entity.Passenger;
import com.capg.exceptions.RecordAlreadyPresentException;
import com.capg.exceptions.RecordNotFoundException;
import com.capg.service.PassengerService;

@RestController
@RequestMapping("/passenger")
public class PassengerController {
	@Autowired
	PassengerService passService;
	
	@PostMapping("/createPassenger")
	@ExceptionHandler(RecordAlreadyPresentException.class)
	public ResponseEntity<?> createPassenger(@RequestBody Passenger passenger){
		
			passService.addPassenger(passenger);
			String msg = "Passenger data registered";
			return new ResponseEntity<>(msg, HttpStatus.ACCEPTED);
	} 
	
	@GetMapping("/readAllPassengers")
	public Iterable<Passenger> readAllPassengers() {

		return passService.displayAllPassengers();
	}

	@PutMapping("/updatePassenger")
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<Passenger> updatePassenger(@RequestBody Passenger passenger) {
		
		Passenger p = passService.updatePassenger(passenger);
		return new ResponseEntity<Passenger>(p, HttpStatus.ACCEPTED);
	}
 
	@GetMapping("/searchPassenger/{id}")
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<?> searchPassengerById(@PathVariable("id") Integer id) {

		return passService.findPassengerById(id);
	}

	@DeleteMapping("/delete/{id}")
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<?> deletePassengerById(@PathVariable("id") Integer id) {
		String msg = passService.deletePassengerById(id);
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
}
