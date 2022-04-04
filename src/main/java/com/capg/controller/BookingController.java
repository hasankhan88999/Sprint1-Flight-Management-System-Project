package com.capg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capg.entity.Booking;
import com.capg.entity.Flight;
import com.capg.entity.Passenger;
import com.capg.entity.Users;
import com.capg.exceptions.RecordAlreadyPresentException;
import com.capg.exceptions.RecordNotFoundException;
import com.capg.service.BookingService;
import com.capg.service.FlightService;
import com.capg.service.PassengerService;
import com.capg.service.UserService;

@ComponentScan(basePackages = "com.capg")
@RestController
@RequestMapping("/booking")
public class BookingController {
	@Autowired(required = true)
	BookingService bookingService;
	
	@Autowired(required = true)
	UserService userService;
	
	@Autowired(required = true)
	PassengerService passengerService;
	
	@Autowired(required = true)
	FlightService flightService;

	@PostMapping("/createBooking")
	@ExceptionHandler(RecordAlreadyPresentException.class)
	public ResponseEntity<?> addBooking(Booking newBooking, @RequestBody List<Passenger> passengerList,
			@RequestParam(name="user_id") Integer user_id, @RequestParam(name="booking_date") String booking_date,
			@RequestParam(name="ticket_cost") double ticket_cost, @RequestParam(name="flight_no") Integer flight_no
			){
		
		Users bookingUser;
		try {
			bookingUser = userService.returnUser(user_id);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
		} 
		
		List<Passenger> passList;
		try {
			passList = passengerService.addAllPassengers(passengerList);
			
		} catch (RecordAlreadyPresentException e) {
			return new ResponseEntity<>("Record already present", HttpStatus.BAD_REQUEST);
		}
		
		Flight flight;
		try {
			flight = flightService.viewFlight(flight_no);
		} catch (RecordNotFoundException e) { 
			return new ResponseEntity<>("Flight not found", HttpStatus.BAD_REQUEST);
		}
		
		newBooking.setUser(bookingUser);
		newBooking.setBookingDate(booking_date);
		newBooking.setPassenger(passList);
		newBooking.setTicketCost(ticket_cost);
		newBooking.setFlight(flight);
		newBooking.setNoOfPassengers(passList.size());
		
		bookingService.createBooking(newBooking);
		String msg = "Booking registered with booking ID: " + newBooking.getBookingId();
		return new ResponseEntity<>(msg, HttpStatus.ACCEPTED);

	}

	@GetMapping("/readAllBooking")
	public Iterable<Booking> readAllBookings() {
 
		return bookingService.displayAllBooking();
	}

	@PutMapping("/updateBooking")
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<Booking> modifyBooking(@RequestBody Booking updateBooking) {
		Booking booking = bookingService.updateBooking(updateBooking);
		return new ResponseEntity<Booking>(booking, HttpStatus.ACCEPTED);

	}

	@GetMapping("/searchBooking/{id}")
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<?> searchBookingByID(@PathVariable("id") Integer bookingId) {

		return bookingService.findBookingById(bookingId);
	}

	@DeleteMapping("/deleteBooking/{id}")
	public ResponseEntity<?> deleteBookingByID(@PathVariable("id") Integer bookingId) {

		String msg = bookingService.deleteBookingByID(bookingId);
		return new ResponseEntity<>(msg, HttpStatus.OK); 
	}
}
