package com.bridgelabz.fundoonotesapi.exception;

/**
 * @author Tejashree Surve
 * @Purpose : This is customize Exception.
 */
public class NoteNotExistInTrash extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoteNotExistInTrash(String message) {
		super(message);
	}
}
