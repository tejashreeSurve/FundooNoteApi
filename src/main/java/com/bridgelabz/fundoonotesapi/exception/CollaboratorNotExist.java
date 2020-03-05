package com.bridgelabz.fundoonotesapi.exception;

public class CollaboratorNotExist extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CollaboratorNotExist(String message) {
		super(message);
	}
}
