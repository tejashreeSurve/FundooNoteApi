package com.bridgelabz.fundoonotesapi.exception;

/**
 * @author Tejashree Surve
 * @Purpose : This is customize Exception.
 */
public class LabelNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LabelNotExistException(String message) {
		super(message);
	}
}
