package com.bridgelabz.fundoonotesapi.exception;

/**
 * @author Tejashree Surve
 * @Purpose : This is customize Exception.
 */
public class LabelAlreadyExist extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LabelAlreadyExist(String message) {
		super(message);
	}
}
