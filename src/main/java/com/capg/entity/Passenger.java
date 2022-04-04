package com.capg.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "passenger")
public class Passenger {
	
	@Id
	private Integer pnrNumber=Integer.MIN_VALUE;
	private Integer passengerUIN=Integer.MIN_VALUE;
	private String passengerName;
	private int passengerAge;
	private Double luggage;
}
