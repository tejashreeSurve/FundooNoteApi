package com.bridgelabz.fundoonotesapi.exception;

public class LabelNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;
 
	public LabelNotExistException(String message) {
		super(message);
	}
}
