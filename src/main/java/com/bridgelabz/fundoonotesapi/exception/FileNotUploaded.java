package com.bridgelabz.fundoonotesapi.exception;

public class FileNotUploaded extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public FileNotUploaded(String message) {
		super(message);
	}
}
