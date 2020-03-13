package com.bridgelabz.fundoonotesapi.exception;

/**
 * @author Tejashree Surve
 * @Purpose : This is customize Exception.
 */
public class NoteNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoteNotFoundException(String message) {
		super(message);
	}
}
