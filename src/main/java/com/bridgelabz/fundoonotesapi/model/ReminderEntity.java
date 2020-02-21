package com.bridgelabz.fundoonotesapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Tejashree Surve
 * @Purpose : This is Entity Class for Reminder DataBase.
 */
@Entity
@Component
@Table(name = "reminder")
@JsonIgnoreProperties({ "noteEntity" })
public class ReminderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NonNull
	private String date;
	@NonNull
	private String time;
	private boolean daily;
	private boolean weekly;
	private boolean monthly;
	private boolean yearly;
	private boolean doNotRepeat;
	
	@OneToOne
	@JoinColumn(name = "note_id" ,nullable = false)
	private NoteEntity noteEntity;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public boolean isDaily() {
		return daily;
	}
	public void setDaily(boolean daily) {
		this.daily = daily;
	}
	public boolean isWeekly() {
		return weekly;
	}
	public void setWeekly(boolean weekly) {
		this.weekly = weekly;
	}
	public boolean isMonthly() {
		return monthly;
	}
	public void setMonthly(boolean monthly) {
		this.monthly = monthly;
	}
	public boolean isYearly() {
		return yearly;
	}
	public void setYearly(boolean yearly) {
		this.yearly = yearly;
	}
	public NoteEntity getNoteEntity() {
		return noteEntity;
	}
	public void setNoteEntity(NoteEntity noteEntity) {
		this.noteEntity = noteEntity;
	}
	public boolean isDoNotRepeat() {
		return doNotRepeat;
	}
	public void setDoNotRepeat(boolean doNotRepeat) {
		this.doNotRepeat = doNotRepeat;
	}
}
