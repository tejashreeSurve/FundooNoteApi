package com.bridgelabz.fundoonotesapi.exception;

/**
 * @author Tejashree Surve
 * @Purpose : This is customize Exception.
 */
public class CollaboratorNotExist extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CollaboratorNotExist(String message) {
		super(message);
	}
}
