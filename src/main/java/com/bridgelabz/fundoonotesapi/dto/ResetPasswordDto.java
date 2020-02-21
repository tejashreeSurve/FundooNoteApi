package com.bridgelabz.fundoonotesapi.dto;

import javax.validation.constraints.Pattern;

/**
 * @author Tejashree Surve
 * @Purpose : This is Data Transfer Object that holds Data for Rest Password Api.
 */
public class ResetPasswordDto {
	@Pattern(regexp = "(?=.*[0-9])" ,message = "Password must contain at least one numeric value")
	private String password;
	private String confirmpassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
}
