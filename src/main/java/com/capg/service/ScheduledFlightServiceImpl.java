package com.capg.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capg.dao.BookingDao;
import com.capg.dao.ScheduleDao;
import com.capg.dao.ScheduledFlightDao;
import com.capg.entity.Booking;
import com.capg.entity.Schedule;
import com.capg.entity.ScheduledFlight;
import com.capg.exceptions.RecordNotFoundException;
import com.capg.exceptions.ScheduledFlightNotFoundException;

@Service
public class ScheduledFlightServiceImpl implements ScheduledFlightService {

	@Autowired
	ScheduledFlightDao dao;

	@Autowired
	ScheduleDao scheduleDao;

	@Autowired
	BookingService bookingService;
	
	@Autowired
	BookingDao bookingDao;

	/*
	 * Service method to add new Scheduled flight to database
	 */
	@Override
	public ScheduledFlight addScheduledFlight(ScheduledFlight scheduledFlight) {
		return dao.save(scheduledFlight);
	}

	/*
	 * Service method to modify existing Scheduled flight in database
	 */
	@Override 
	public ScheduledFlight modifyScheduledFlight(ScheduledFlight scheduleFlight) {
		ScheduledFlight updateScheduleFlight = dao.findById(scheduleFlight.getScheduleFlightId()).get();
		Schedule updateSchedule = scheduleDao.findById(scheduleFlight.getSchedule().getScheduleId()).get();
		updateScheduleFlight.setAvailableSeats(scheduleFlight.getAvailableSeats());
		updateSchedule.setSrcAirport(scheduleFlight.getSchedule().getSrcAirport());
		updateSchedule.setDstnAirport(scheduleFlight.getSchedule().getDstnAirport());
		updateSchedule.setArrDateTime(scheduleFlight.getSchedule().getArrDateTime());
		updateSchedule.setDeptDateTime(scheduleFlight.getSchedule().getDeptDateTime());
		dao.save(updateScheduleFlight);
		return scheduleFlight;
	}

	/*
	 * Service method to remove existing Scheduled flight from database
	 */
	@Override
	public String removeScheduledFlight(Integer flightId) throws RecordNotFoundException {
		if (flightId == null)
			throw new RecordNotFoundException("Enter flight Id!");
		Optional<ScheduledFlight> scheduleFlight = dao.findById(flightId);
		if (!scheduleFlight.isPresent())
			throw new RecordNotFoundException("Enter a valid Flight Id");
		else {
			dao.deleteById(flightId);
		}
		return "Scheduled flight with ID " + flightId + " is not found";
	}


	/*
	 * Service method to view all Scheduled flights in database
	 */
	@Override
	public Iterable<ScheduledFlight> viewAllScheduledFlights() {
		return dao.findAll();
	}

	/*
	 * Service method to view a Scheduled flight by ID from database
	 */
	@Override
	public ScheduledFlight viewScheduledFlight(Integer flightId) throws ScheduledFlightNotFoundException {
		if (flightId == null) 
			throw new ScheduledFlightNotFoundException("Enter flight Id");
		Optional<ScheduledFlight> scheduleFlight = dao.findById(flightId);
		if (!scheduleFlight.isPresent())
			throw new ScheduledFlightNotFoundException("Enter a valid Flight Id");
		else
			return scheduleFlight.get();
	}
	
	/*
	 * Service method to get the available seats in the flight
	 * Checks the number of passengers seats booked and how many are still free to book
	 */
	@Override
	public Integer getAvailableSeats(List<Integer> bookingIds, Integer flightId) {
		if(bookingIds == null || bookingIds.isEmpty()) {
			throw new RecordNotFoundException("Booking ID's not provided");
		}
		List<Booking> bookings = new ArrayList<>();
		Iterator<Integer> itr = bookingIds.iterator();
		
		/*
		 * Iterating through the Booking by specified booking_ids
		 */
		while(itr.hasNext()) {
			Optional<Booking> iterBooking = bookingDao.findById(itr.next());
			if(!iterBooking.isPresent()) {
				throw new RecordNotFoundException("Booking with ID's provided not found");
			}else {
				if(iterBooking.get().getFlight().getFlightNo() == flightId) {
					bookings.add(iterBooking.get());
				}else throw new RecordNotFoundException("Flight with ID: "+flightId+" is not registered");
				
			}
		}
		
		/*
		 * Checks the total seats booked and returns it
		 */
		Iterator<Booking> bkItr = bookings.iterator();
		int totalSeats=0;
		while(bkItr.hasNext()) {
			totalSeats += bkItr.next().getNoOfPassengers();
		}
		
		
		return totalSeats;
		
		
	}

}
