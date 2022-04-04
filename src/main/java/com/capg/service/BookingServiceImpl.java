package com.capg.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.capg.dao.BookingDao;
import com.capg.dao.PassengerDao;
import com.capg.entity.Booking;
import com.capg.entity.Passenger;
import com.capg.exceptions.RecordAlreadyPresentException;
import com.capg.exceptions.RecordNotFoundException;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingDao bookingDao;
	
	@Autowired
	PassengerDao passengerDao;
	
	/*
	 * create a new booking
	 */
	@Override
	public ResponseEntity<Booking> createBooking(Booking newBooking) {

		Optional<Booking> findBookingById = bookingDao.findById(newBooking.getBookingId());
		try {
			if (!findBookingById.isPresent()) {
				int val = new Random().nextInt(1000, 2700034);
				newBooking.setBookingId(val);
				bookingDao.save(newBooking);
				return new ResponseEntity<Booking>(newBooking, HttpStatus.OK);
			} else {
				throw new RecordAlreadyPresentException("Booking with Booking Id: " + newBooking.getBookingId() + " already exists!!");
			}
				
		} catch (RecordAlreadyPresentException e) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * update the existing booking
	 */
	@Override
	public Booking updateBooking(Booking changedBooking) {
		Optional<Booking> findBookingById = bookingDao.findById(changedBooking.getBookingId());
		if (findBookingById.isPresent()) {
			bookingDao.save(changedBooking);
		} else
			throw new RecordNotFoundException(
					"Booking with Booking Id: " + changedBooking.getBookingId() + " not exists!!");
		return changedBooking;
	}

	
	/*
	 * delete the existing booking
	 */
	@Override
	public String deleteBookingByID(Integer bookingId) {

		Optional<Booking> findBookingById = bookingDao.findById(bookingId);
		if (findBookingById.isPresent()) {
			List<Passenger> pss = findBookingById.get().getPassenger();
			Iterator<Passenger> itr = pss.iterator();
			while(itr.hasNext()) {
				Passenger ps = itr.next();
				passengerDao.deleteById(ps.getPnrNumber()); 
				
			}
			
			bookingDao.deleteById(bookingId);
			return "Booking Deleted!!";
		} else {
			throw new RecordNotFoundException("Booking not found for the entered BookingID");
		}
		
			
	}

	/*
	 * display all the bookings registered
	 */
	@Override
	public Iterable<Booking> displayAllBooking() {

		return bookingDao.findAll();
	}

	/*
	 * find booking by booking_id
	 */
	@Override
	public ResponseEntity<?> findBookingById(Integer bookingId) {
		Optional<Booking> findById = bookingDao.findById(bookingId);
		try {
			if (findById.isPresent()) {
				Booking findBooking = findById.get();
				return new ResponseEntity<Booking>(findBooking, HttpStatus.OK);
			} else
				throw new RecordNotFoundException("No record found with ID " + bookingId);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}


}
