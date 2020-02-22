package com.bridgelabz.fundoonotesapi.exception;

/**
 * @author Tejashree Surve
 * @Purpose : This is customize 
 */
public class ForgetPasswordException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ForgetPasswordException(String message) {
		super(message);
	}
}
