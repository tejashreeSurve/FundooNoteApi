package com.bridgelabz.fundoonotesapi.dto;

import javax.validation.constraints.Pattern;

import org.springframework.lang.NonNull;

/**
 * @author Tejashree Surve
 * @Purpose : This is Data Transfer Object that holds Data for User Api.
 */
public class UserDto {
	@NonNull
	private String firstname;
	@NonNull
	private String lastname;
	@Pattern(regexp = "((([0]{1}[1-9]{1})|([1]{1}[0-9]{1})|([2]{1}[0-9]{1})|([3]{1}[0-1]{1}))/(([0]{1}[1-9]{1})|([1]{1}[0-2]{1}))/([1-9]{1}[0-9]{1}[0-9]{1}[1-9]{1}))",message = "Please Enter valide date")
	private String birthdate;
	@Pattern(regexp = "^\\d{10}",message ="Please Enter valide Phone-Number")
	private String phonenumber;
	@NonNull
	private String city;
	@Pattern(regexp = "\"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@\"\n" + 
			"+ \"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$\"",message = "Please Enter valide Email")
	private String email;
	@Pattern(regexp = "(?=.*[0-9])" ,message = "Password must contain at least one numeric value")
	private String userpassword;
	private boolean isValidate;
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public boolean isValidate() {
		return isValidate;
	}

	public void setValidate(boolean isValidate) {
		this.isValidate = isValidate;
	}
}
