package com.bridgelabz.fundoonotesapi.dto;

import org.springframework.lang.NonNull;

/**
 * @author Tejashree Surve
 * @Purpose : This is Data Transfer Object class for label Api's. 
 */
public class LabelDto {
	@NonNull
	private String labelname;

	public String getLabelname() {
		return labelname;
	}

	public void setLabelname(String labelname) {
		this.labelname = labelname;
	}
}
