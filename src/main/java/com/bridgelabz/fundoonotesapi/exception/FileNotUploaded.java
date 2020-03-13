package com.bridgelabz.fundoonotesapi.exception;

/**
 * @author Tejashree Surve
 * @Purpose : This is customize Exception.
 */
public class FileNotUploaded extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileNotUploaded(String message) {
		super(message);
	}
}
