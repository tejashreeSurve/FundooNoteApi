package com.bridgelabz.fundoonotesapi.dto;

import org.springframework.lang.NonNull;
/**
 * @author Tejashree Surve
 * @Purpose : This is Data Transfer Object class for Notes Api. 
 */
public class NoteDto {
	@NonNull
	private String title;
	@NonNull
	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
