package com.bridgelabz.fundoonotesapi.exception;

public class LabelAlreadyExist extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public LabelAlreadyExist(String message) {
		super(message);
	}
}
