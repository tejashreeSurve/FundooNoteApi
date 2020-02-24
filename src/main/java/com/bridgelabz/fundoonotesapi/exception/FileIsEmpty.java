package com.bridgelabz.fundoonotesapi.exception;
/**
 * @author Tejashree Surve
 * @Purpose : This is customize Exception.
 */
public class FileIsEmpty extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileIsEmpty(String message) {
		super(message);
	}
}
