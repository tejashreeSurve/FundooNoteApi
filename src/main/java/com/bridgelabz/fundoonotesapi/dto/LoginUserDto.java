package com.bridgelabz.fundoonotesapi.dto;

import javax.validation.constraints.Pattern;

/**
 * @author Tejashree Surve
 * @Purpose : This is Data Transfer Object that holds Data for Login User Api's.
 */
public class LoginUserDto {
	@Pattern(regexp = "\"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@\"\n"
			+ "+ \"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$\"", message = "Please Enter valide Email")
	private String email;
	@Pattern(regexp = "(?=.*[0-9])", message = "Password must contain at least one numeric value")
	private String userPassword;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}
