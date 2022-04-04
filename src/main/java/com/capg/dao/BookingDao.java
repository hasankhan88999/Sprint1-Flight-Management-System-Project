package com.capg.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capg.entity.Booking;

@Repository
public interface BookingDao extends CrudRepository<Booking, Integer> {

}
