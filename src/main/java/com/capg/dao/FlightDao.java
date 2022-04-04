package com.capg.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capg.entity.Flight;
@Repository
public interface FlightDao extends CrudRepository<Flight, Integer>{

}
