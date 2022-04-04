package com.capg.service;

import org.springframework.http.ResponseEntity;

import com.capg.entity.Booking;

public interface BookingService {
	public ResponseEntity<?> createBooking(Booking newBooking);

	public Booking updateBooking(Booking newBooking);

	public String deleteBookingByID(Integer bookingId);

	public Iterable<Booking> displayAllBooking();

	public ResponseEntity<?> findBookingById(Integer bookingId);

	
}
