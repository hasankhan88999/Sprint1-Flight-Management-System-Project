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

import com.capg.entity.Airport;
import com.capg.exceptions.RecordAlreadyPresentException;
import com.capg.exceptions.RecordNotFoundException;
import com.capg.service.AirportService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/airport")
public class AirportController {
	@Autowired(required = true)
	AirportService airportService;
	
	@GetMapping("/viewAirport/{id}")
	@ExceptionHandler(RecordNotFoundException.class)
	public Airport viewAirport(@PathVariable("id") String airportCode) {
		return airportService.viewAirport(airportCode);
	}
	
	@GetMapping("/allAirport")
	public Iterable<Airport> viewAllAirport(){
		return airportService.viewAllAirport();
	}
	
	@PostMapping("/addAirport")
	@ExceptionHandler(RecordAlreadyPresentException.class)
	public ResponseEntity<?> addAirport(@RequestBody Airport airport) {
		boolean isDataExist = airportService.checkDataExistence(airport);
		if(isDataExist) {
			String msg = "Airport with ID: "+airport.getAirportCode()+" has been already registered!";
			return new ResponseEntity<>(msg, HttpStatus.ALREADY_REPORTED);
		}
		else{
			airportService.addAirport(airport);
			String msg = "Airport is registered!";
			return new ResponseEntity<>(msg, HttpStatus.ALREADY_REPORTED);
		}
	}
	
	@PutMapping("/updateAirport")
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<Airport> modifyAirport(@RequestBody Airport airport) {
		Airport air = airportService.modifyAirport(airport);
		return new ResponseEntity<Airport>(air, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/deleteAirport/{id}")
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<?> removeAirport(@PathVariable("id") String airportCode) {
		 String msg = airportService.removeAirport(airportCode);
		 return new ResponseEntity<>(msg, HttpStatus.OK);
		 
	}
}








