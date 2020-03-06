package com.bridgelabz.fundoonotesapi.exception;

public class NoteNotExistInTrash extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NoteNotExistInTrash(String message) {
		super(message);
	}
}
