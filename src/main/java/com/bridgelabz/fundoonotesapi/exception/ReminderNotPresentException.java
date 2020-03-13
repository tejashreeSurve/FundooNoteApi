package com.bridgelabz.fundoonotesapi.exception;

/**
 * @author Tejashree Surve
 * @Purpose : This is customize Exception.
 */
public class ReminderNotPresentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReminderNotPresentException(String message) {
		super(message);
	}
}
