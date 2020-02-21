package com.bridgelabz.fundoonotesapi.dto;

/**
 * @author Tejashree Surve
 * @Purpose : This is Data Transfer Object class for change label Api.
 */
public class ChangeLabelDto {
	private int preLabelId;
	private int changeLabelId;

	public int getPreLabelId() {
		return preLabelId;
	}

	public void setPreLabelId(int preLabelId) {
		this.preLabelId = preLabelId;
	}

	public int getChangeLabelId() {
		return changeLabelId;
	}

	public void setChangeLabelId(int changeLabelId) {
		this.changeLabelId = changeLabelId;
	}
}
