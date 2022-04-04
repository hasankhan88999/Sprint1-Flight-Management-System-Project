package com.capg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedule")
public class Schedule {
	@Id
	@Column(name = "schedule_Id")
	private Integer scheduleId;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Airport srcAirport;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Airport dstnAirport;
	
	@Column(name = "departure_date")
	private String deptDateTime;
	
	@Column(name = "arrival_date")
	private String arrDateTime;
	
	@Override
	public String toString() {
		return "Schedule [scheduleId=" + scheduleId + ", sourceAirport=" + srcAirport + ", destinationAirport="
				+ dstnAirport + ", departureDateTime=" + deptDateTime + ", arrivalDateTime="
				+ arrDateTime + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrDateTime == null) ? 0 : arrDateTime.hashCode());
		result = prime * result + ((deptDateTime == null) ? 0 : deptDateTime.hashCode());
		result = prime * result + ((dstnAirport == null) ? 0 : dstnAirport.hashCode());
		result = prime * result + ((scheduleId == null) ? 0 : scheduleId.hashCode());
		result = prime * result + ((srcAirport == null) ? 0 : srcAirport.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		if (arrDateTime == null) {
			if (other.arrDateTime != null)
				return false;
		} else if (!arrDateTime.equals(other.arrDateTime))
			return false;
		if (deptDateTime == null) {
			if (other.deptDateTime != null)
				return false;
		} else if (!deptDateTime.equals(other.deptDateTime))
			return false;
		if (dstnAirport == null) {
			if (other.dstnAirport != null)
				return false;
		} else if (!dstnAirport.equals(other.dstnAirport))
			return false;
		if (scheduleId == null) {
			if (other.scheduleId != null)
				return false;
		} else if (!scheduleId.equals(other.scheduleId))
			return false;
		if (srcAirport == null) {
			if (other.srcAirport != null)
				return false;
		} else if (!srcAirport.equals(other.srcAirport))
			return false;
		return true;
	}
}





