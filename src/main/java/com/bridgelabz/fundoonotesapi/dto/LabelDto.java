package com.bridgelabz.fundoonotesapi.dto;

import org.springframework.lang.NonNull;

/**
 * @author Tejashree Surve
 * @Purpose : This is Data Transfer Object class for label Api's.
 */
public class LabelDto {
	@NonNull
	private String labelName;

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
}
