package com.capg.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.capg.dao.PassengerDao;
import com.capg.entity.Passenger;
import com.capg.exceptions.RecordAlreadyPresentException;
import com.capg.exceptions.RecordNotFoundException;

@Service
public class PassengerServiceImpl implements PassengerService {

	@Autowired
	PassengerDao passengerDao;
	
	/*
	 * add a new passenger
	 */
	@Override
	public ResponseEntity<?> addPassenger(Passenger passenger) {
		// TODO Auto-generated method stub
		Optional<Passenger> findUserById = passengerDao.findById(passenger.getPnrNumber());
		try {
			if (!findUserById.isPresent()) {
				int pnrNumber = new Random().nextInt(50000, 100000);
				int uinNumber = new Random().nextInt(1000, 80000);

				passenger.setPnrNumber(pnrNumber);
				passenger.setPassengerUIN(uinNumber);

				passengerDao.save(passenger);
				return new ResponseEntity<>(passenger, HttpStatus.OK);
			} else
				throw new RecordAlreadyPresentException(
						"Passenger with UIN: " + passenger.getPassengerUIN() + " already exists!!");
		} catch (RecordAlreadyPresentException e) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/*
	 * display all existing passenger
	 */
	@Override
	public Iterable<Passenger> displayAllPassengers() {
		// TODO Auto-generated method stub
		return passengerDao.findAll();
	}
	
	/*
	 * update an existing passenger
	 */
	@Override
	public Passenger updatePassenger(Passenger passenger) {
		// TODO Auto-generated method stub
		Optional<Passenger> findUserById = passengerDao.findById(passenger.getPassengerUIN());
		if (findUserById.isPresent()) {
			passengerDao.save(passenger);
		} else
			throw new RecordNotFoundException("Passenger with UIN: " + passenger.getPassengerUIN() + " not exists!!");
		return passenger;
	}
	
	/*
	 * find passenger by passenger_id
	 */
	@Override
	public ResponseEntity<Passenger> findPassengerById(Integer passId) {
		// TODO Auto-generated method stub
		Optional<Passenger> findById = passengerDao.findById(passId);
		try {
			if (findById.isPresent()) {
				Passenger findPassenger = findById.get();
				return new ResponseEntity<Passenger>(findPassenger, HttpStatus.OK);
			} else
				throw new RecordNotFoundException("No record found with ID " + passId);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	/*
	 * delete an existing passenger by passenger_id
	 */
	@Override
	public String deletePassengerById(Integer passId) {
		// TODO Auto-generated method stub
		Optional<Passenger> findPassengerById = passengerDao.findById(passId);
		if (findPassengerById.isPresent()) {
			passengerDao.deleteById(passId);
			return "Passenger Deleted!!";
		} else
			throw new RecordNotFoundException("Passenger not found for the entered UserID");
	}
	
	/*
	 * check passenger existence
	 */
	@Override
	public boolean checkDataExistence(Integer passenger) {
		// TODO Auto-generated method stub
		Optional<Passenger> pass = passengerDao.findById(passenger);
		if (pass.isPresent()) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * add all passengers provided by the controller
	 * iterating through all the passenger list
	 * generating their pnr and uin
	 * setting the rest data as per requirement by the controller class
	 */
	@Override
	public List<Passenger> addAllPassengers(List<Passenger> passengerList) {
		Iterator<Passenger> itr = passengerList.iterator();
		List<Passenger> resultList = new ArrayList<>();
		while (itr.hasNext()) {
			Passenger pass = itr.next();

			int pnrNumber = new Random().nextInt(50000, 100000);
			int uinNumber = new Random().nextInt(1000, 80000);

			pass.setPnrNumber(pnrNumber);
			pass.setPassengerUIN(uinNumber);
			pass.setPassengerName(pass.getPassengerName());
			pass.setPassengerAge(pass.getPassengerAge());
			pass.setLuggage(pass.getLuggage());

			passengerDao.save(pass);
			resultList.add(pass);
		}
		return resultList;
	}

}
