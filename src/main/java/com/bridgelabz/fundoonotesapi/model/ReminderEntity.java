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
	private boolean repeatDaily;
	private boolean repeatWeekly;
	private boolean repeatMonthly;
	private boolean repeatYearly;
	private boolean doNotRepeat = true;

	@OneToOne
	@JoinColumn(name = "note_id", nullable = false)
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

	public boolean isRepeatDaily() {
		return repeatDaily;
	}

	public void setRepeatDaily(boolean repeatDaily) {
		this.repeatDaily = repeatDaily;
	}

	public boolean isRepeatWeekly() {
		return repeatWeekly;
	}

	public void setRepeatWeekly(boolean repeatWeekly) {
		this.repeatWeekly = repeatWeekly;
	}

	public boolean isRepeatMonthly() {
		return repeatMonthly;
	}

	public void setRepeatMonthly(boolean repeatMonthly) {
		this.repeatMonthly = repeatMonthly;
	}

	public boolean isRepeatYearly() {
		return repeatYearly;
	}

	public void setRepeatYearly(boolean repeatYearly) {
		this.repeatYearly = repeatYearly;
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
