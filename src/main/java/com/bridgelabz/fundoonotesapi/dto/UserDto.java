package com.bridgelabz.fundoonotesapi.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.lang.NonNull;

/**
 * @author Tejashree Surve
 * @Purpose : This is Data Transfer Object that holds Data for User Api's.
 */
public class UserDto {
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	@NotEmpty
	@Pattern(regexp = "((([0]{1}[1-9]{1})|([1]{1}[0-9]{1})|([2]{1}[0-9]{1})|([3]{1}[0-1]{1}))/(([0]{1}[1-9]{1})|([1]{1}[0-2]{1}))/([1-9]{1}[0-9]{1}[0-9]{1}[1-9]{1}))", message = "Please Enter valide date")
	private String birthdate;
	@NotEmpty
	@Pattern(regexp = "^\\d{10}", message = "Please Enter valide Phone-Number")
	private String phoneNumber;
	
	@NotEmpty
	private String city;
	@NotEmpty
	@Pattern(regexp = "\"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@\"\n"
			+ "+ \"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$\"", message = "Please Enter valide Email")
	private String email;
	@NotEmpty
	@Pattern(regexp = "(?=.*[0-9])", message = "Password must contain at least one numeric value")
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

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
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
