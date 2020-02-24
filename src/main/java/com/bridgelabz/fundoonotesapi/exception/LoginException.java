package com.bridgelabz.fundoonotesapi.exception;
/**
 * @author Tejashree Surve
 * @Purpose : This is customize Exception.
 */
public class LoginException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LoginException(String message) {
		super(message);
	}
}
