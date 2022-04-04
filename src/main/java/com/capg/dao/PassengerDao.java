package com.capg.dao;

import org.springframework.data.repository.CrudRepository;

import com.capg.entity.Passenger;

public interface PassengerDao extends CrudRepository<Passenger, Integer> {
	public Passenger findByPassengerUIN(Integer passengerUin);
}
