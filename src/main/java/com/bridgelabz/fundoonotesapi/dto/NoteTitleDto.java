package com.bridgelabz.fundoonotesapi.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author Tejashree Surve
 * @Purpose : This is Data Transfer Object class for Notes Title Api's.
 */
public class NoteTitleDto {
	@NotEmpty
	private String noteTitle;

	public String getNoteTitle() {
		return noteTitle;
	}

	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}
}
