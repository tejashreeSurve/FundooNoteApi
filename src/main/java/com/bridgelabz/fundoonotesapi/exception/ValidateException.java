package com.bridgelabz.fundoonotesapi.exception;
/**
 * @author Tejashree Surve
 * @Purpose : This is customize Exception.
 */
public class ValidateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ValidateException(String message) {
		super(message);
	}
}
