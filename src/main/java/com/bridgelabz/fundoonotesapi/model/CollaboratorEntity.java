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
 * @Purpose : This is Entity Class for Collaborator Entity.
 */
@Component
@Entity
@Table(name = "collaborator")
@JsonIgnoreProperties({ "noteEntity" })
public class CollaboratorEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String mailReciver;
	private String mailSender;

	@ManyToOne
	@JoinColumn(name = "note_id", nullable = false)
	private NoteEntity noteEntity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMailReciver() {
		return mailReciver;
	}

	public void setMailReciver(String mailReciver) {
		this.mailReciver = mailReciver;
	}

	public String getMailSender() {
		return mailSender;
	}

	public void setMailSender(String mailSender) {
		this.mailSender = mailSender;
	}

	public NoteEntity getNoteEntity() {
		return noteEntity;
	}

	public void setNoteEntity(NoteEntity noteEntity) {
		this.noteEntity = noteEntity;
	}
}
