package com.bridgelabz.fundoonotesapi.dto;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

/**
 * @author Tejashree Surve
 * @Purpose : This is Data Transfer Object that holds Data for User Api's.
 */
public class UserDto {
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	@NotNull
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate birthdate;
	@NotEmpty
	@Pattern(regexp = "^\\d{10}", message = "Please Enter valide Phone-Number")
	private String phoneNumber;
	
	@NotEmpty
	private String city;
	@NotEmpty
	@Pattern(regexp = "\\w+\\@\\w+\\.\\w+", message = "Please Enter valide Email")
	private String email;
	@NotEmpty
	@Pattern(regexp = "\\w+\\d+", message = "Password must contain at least one numeric value")
	private String userPassword;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}
