package com.bridgelabz.fundoonotesapi.exception;

public class ReminderNotPresentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReminderNotPresentException(String message) {
		super(message);
	}
}
