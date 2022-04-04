package com.capg.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking")
public class Booking {
	
	@Id
	private Integer bookingId=Integer.MIN_VALUE;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	private Users user;

	private String bookingDate;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Passenger> passenger; 
	
	private double ticketCost; 
	 
	@OneToOne(cascade = CascadeType.PERSIST) 
	private Flight flight;
	
	private int noOfPassengers;
}
