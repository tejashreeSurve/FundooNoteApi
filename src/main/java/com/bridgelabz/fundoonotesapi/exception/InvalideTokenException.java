package com.bridgelabz.fundoonotesapi.exception;
/**
 * @author Tejashree Surve
 * @Purpose : This is customize Exception.
 */
public class InvalideTokenException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InvalideTokenException(String message) {
		super(message);
	}
}
