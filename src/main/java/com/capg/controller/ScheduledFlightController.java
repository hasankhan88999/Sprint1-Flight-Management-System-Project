package com.capg.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capg.entity.Schedule;
import com.capg.entity.ScheduledFlight;
import com.capg.exceptions.RecordNotFoundException;
import com.capg.exceptions.ScheduledFlightNotFoundException;
import com.capg.service.AirportService;
import com.capg.service.BookingService;
import com.capg.service.FlightService;
import com.capg.service.ScheduledFlightService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/scheduledFlight")
public class ScheduledFlightController {
	@Autowired
	ScheduledFlightService scheduleFlightService;

	@Autowired
	AirportService airportService;

	@Autowired
	FlightService flightService;
	 
	@PostMapping("/add")
	public ResponseEntity<ScheduledFlight> addSF(ScheduledFlight scheduledFlight,
			@RequestParam(name = "flightId") Integer flightId, @RequestParam(name="bookingIds") List<Integer> bookingIds,
			@RequestParam(name = "srcAirportCode") String srcAirportCode, @RequestParam(name = "dstnAirportCode") String dstnAirportCode,
			@RequestParam(name = "deptDateTime") String deptDateTime, @RequestParam(name = "arrDateTime") String arrDateTime) {
		Schedule schedule = new Schedule();
		int ScheduleIdValue = new Random().nextInt(10000, 30000);
		schedule.setScheduleId( ScheduleIdValue );
		try {
			schedule.setSrcAirport(airportService.viewAirport(srcAirportCode));
		} catch (RecordNotFoundException e) {
			return new ResponseEntity("Airport Not Found", HttpStatus.BAD_REQUEST);
		}
		try {
			schedule.setDstnAirport(airportService.viewAirport(dstnAirportCode));
		} catch (RecordNotFoundException e) {
			return new ResponseEntity("Airport Not Found", HttpStatus.BAD_REQUEST);
		}
		schedule.setDeptDateTime(deptDateTime);
		schedule.setArrDateTime(arrDateTime);
		
		try {
			int ScheduleFlightIdValue = new Random().nextInt(20000, 60000);
			scheduledFlight.setScheduleFlightId(ScheduleFlightIdValue);
		} catch (RecordNotFoundException e1) {
			return new ResponseEntity("Flight with ID: "+flightId+" not found", HttpStatus.BAD_REQUEST);
		}
		
		try {
		
			scheduledFlight.setFlight(flightService.viewFlight(flightId));
		} catch (RecordNotFoundException e1) {
			return new ResponseEntity("Flight Not Found", HttpStatus.BAD_REQUEST);
		}
		scheduledFlight.setSchedule(schedule);
//		scheduledFlight.setAvailableSeats(scheduledFlight.getFlight().getSeatCapacity());
		scheduledFlight.setAvailableSeats(scheduledFlight.getFlight().getSeatCapacity() - scheduleFlightService.getAvailableSeats(bookingIds, flightId));
		try {
			return new ResponseEntity<ScheduledFlight>(scheduleFlightService.addScheduledFlight(scheduledFlight),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity("Error adding Flight." + e, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PutMapping("/modify")
	public ResponseEntity<ScheduledFlight> modifyScheduleFlight(@ModelAttribute ScheduledFlight scheduleFlight) {
		ScheduledFlight modifySFlight = scheduleFlightService.modifyScheduledFlight(scheduleFlight);
		if (modifySFlight == null) {
			return new ResponseEntity("Flight not modified", HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<ScheduledFlight>(modifySFlight, HttpStatus.OK);
		}

	}
	
	@DeleteMapping("/delete")
	public String deleteSF(@RequestParam(name = "scheduledFId") Integer flightId) throws RecordNotFoundException {
		return scheduleFlightService.removeScheduledFlight(flightId);
	}
	
	@GetMapping("/search")
	@ExceptionHandler(ScheduledFlightNotFoundException.class)
	public ResponseEntity<ScheduledFlight> viewSF(@RequestParam Integer flightId) throws ScheduledFlightNotFoundException {
		ScheduledFlight searchSFlight = scheduleFlightService.viewScheduledFlight(flightId);
		if (searchSFlight == null) {
			return new ResponseEntity("Flight not present", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<ScheduledFlight>(searchSFlight, HttpStatus.OK);
		}
	}
	
	@GetMapping("/viewAll")
	public Iterable<ScheduledFlight> viewAllSF() {
		return scheduleFlightService.viewAllScheduledFlights();
	}
}







