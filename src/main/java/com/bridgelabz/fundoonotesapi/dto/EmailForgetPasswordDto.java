package com.bridgelabz.fundoonotesapi.dto;

import javax.validation.constraints.Pattern;

/**
 * @author Tejashree Surve
 * @Purpose : This is Data Transfer Object that holds Data for EmailForgetPassword Api.
 */
public class EmailForgetPasswordDto{
	@Pattern(regexp = "\"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@\"\n" + 
			"+ \"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$\"",message = "Please Enter valide Email")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
