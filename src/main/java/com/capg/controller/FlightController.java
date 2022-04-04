package com.capg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.entity.Flight;
import com.capg.exceptions.RecordAlreadyPresentException;
import com.capg.exceptions.RecordNotFoundException;
import com.capg.service.FlightService;

@RestController
@RequestMapping("/flight")
public class FlightController {
	@Autowired(required = true)
	FlightService flightService;

	@PostMapping("/addFlight")
	@ExceptionHandler(RecordAlreadyPresentException.class)
	public ResponseEntity<?> addFlight(@RequestBody Flight flight) {
		boolean isDataExist = flightService.checkDataExistence(flight);
		if(isDataExist) {
			String msg = "Flight has already been registered";
			return new ResponseEntity<>(msg, HttpStatus.ALREADY_REPORTED);
		}else {
			flightService.addFlight(flight);
			String msg = "Flight data registered";
			return new ResponseEntity<>(msg, HttpStatus.ACCEPTED);
		}
		
	}

	@GetMapping("/allFlight")
	public Iterable<Flight> viewAllFlight() {
		return flightService.viewAllFlight();
	}

	@GetMapping("/viewFlight/{id}")
	@ExceptionHandler(RecordNotFoundException.class)
	public Flight viewFlight(@PathVariable("id") Integer flightNo) {
		return flightService.viewFlight(flightNo);
	}

	@PutMapping("/updateFlight")
	@ExceptionHandler(RecordNotFoundException.class)
	public Flight modifyFlight(@RequestBody Flight flight) {
		return flightService.modifyFlight(flight);
	}
	
	@DeleteMapping("/deleteFlight/{id}")
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<?> removeFlight(@PathVariable("id") Integer flightNo) {
		String msg = flightService.removeFlight(flightNo);
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
}
