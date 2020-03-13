package com.bridgelabz.fundoonotesapi.exception;

/**
 * @author Tejashree Surve
 * @Purpose : This is customize Exception.
 */
public class ResetPasswordException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResetPasswordException(String message) {
		super(message);
	}
}
