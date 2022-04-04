package com.capg.service;

import org.springframework.http.ResponseEntity;

import com.capg.entity.Flight;

public interface FlightService {
	public ResponseEntity<?> addFlight(Flight flight);

	public Iterable<Flight> viewAllFlight();

	public Flight viewFlight(Integer flightNumber);

	public Flight modifyFlight(Flight flight);

	public String removeFlight(Integer flightNumber);
	
	public boolean checkDataExistence(Flight flight);
}
