package com.capg.service;

import java.util.List;

import com.capg.entity.ScheduledFlight;
import com.capg.exceptions.RecordNotFoundException;
import com.capg.exceptions.ScheduledFlightNotFoundException;

public interface ScheduledFlightService {
	public ScheduledFlight addScheduledFlight(ScheduledFlight scheduledFlight);

	public ScheduledFlight modifyScheduledFlight(ScheduledFlight scheduledFlight);

	public String removeScheduledFlight(Integer id) throws RecordNotFoundException;

	public Iterable<ScheduledFlight> viewAllScheduledFlights();

	public ScheduledFlight viewScheduledFlight(Integer id) throws ScheduledFlightNotFoundException;

	public Integer getAvailableSeats(List<Integer> bookingIds, Integer flightId);
}
