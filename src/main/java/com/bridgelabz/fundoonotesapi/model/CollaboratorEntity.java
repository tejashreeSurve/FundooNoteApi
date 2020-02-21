package com.bridgelabz.fundoonotesapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Tejashree Surve
 * @Purpose : This is Entity Class for Collaborator DataBase.
 */
@Component
@Entity
@Table(name = "collaborator")
@JsonIgnoreProperties({ "noteEntity" })
public class CollaboratorEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String mailreciver;
	private String mailsender;

	@ManyToOne
	@JoinColumn(name = "note_id", nullable = false)
	private NoteEntity noteEntity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMailreciver() {
		return mailreciver;
	}

	public void setMailreciver(String mailreciver) {
		this.mailreciver = mailreciver;
	}

	public String getMailsender() {
		return mailsender;
	}

	public void setMailsender(String mailsender) {
		this.mailsender = mailsender;
	}

	public NoteEntity getNoteEntity() {
		return noteEntity;
	}

	public void setNoteEntity(NoteEntity noteEntity) {
		this.noteEntity = noteEntity;
	}
}
