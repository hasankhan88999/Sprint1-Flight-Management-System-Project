package com.capg.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capg.entity.Airport;

@Repository
public interface AirportDao extends CrudRepository<Airport, String> {

}
