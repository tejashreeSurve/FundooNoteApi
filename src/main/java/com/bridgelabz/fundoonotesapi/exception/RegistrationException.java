package com.bridgelabz.fundoonotesapi.exception;

/**
 * @author Tejashree Surve
 * @Purpose : This is customize Exception.
 */
public class RegistrationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RegistrationException(String message) {
		super(message);
	}
}
