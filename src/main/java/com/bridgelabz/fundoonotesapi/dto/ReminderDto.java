package com.bridgelabz.fundoonotesapi.dto;

import javax.validation.constraints.Pattern;

import org.springframework.lang.NonNull;
/**
 * @author Tejashree Surve
 * @Purpose : This is Data Transfer Object class for Reminder Api. 
 */
public class ReminderDto {
	@NonNull
	private String date;
	@NonNull
	@Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]" ,message = "Time should be in 24 format HH:MM")
	private String time;

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
