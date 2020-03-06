package com.bridgelabz.fundoonotesapi.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

/**
 * @author Tejashree Surve
 * @Purpose : This is Data Transfer Object class for Reminder Api's.
 */
public class ReminderDto {
	@NotNull
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date date ;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
