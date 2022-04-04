package com.capg.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.capg.entity.Passenger;

public interface PassengerService {
	public ResponseEntity<?> addPassenger(Passenger passenger);
	
	public Iterable<Passenger> displayAllPassengers();
	
	public Passenger updatePassenger(Passenger passenger);
	
	
	public boolean checkDataExistence(Integer passenger);

	public ResponseEntity<?> findPassengerById(Integer id);

	public String deletePassengerById(Integer id);

	public List<Passenger> addAllPassengers(List<Passenger> passengerList);
}
