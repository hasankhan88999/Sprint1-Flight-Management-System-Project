package com.capg.exceptions;

public class ScheduledFlightNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ScheduledFlightNotFoundException(String str) {
		super(str);
	}

}
