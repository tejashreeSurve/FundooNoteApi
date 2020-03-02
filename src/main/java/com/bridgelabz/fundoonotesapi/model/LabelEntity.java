package com.bridgelabz.fundoonotesapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

/**
 * @author Tejashree Surve
 * @Purpose : This is Entity Class for Label Entity.
 */
@Component
@Entity
@Table(name = "label")
public class LabelEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String labelName;
	@ManyToMany
	@JoinTable(name = "noteLabel", joinColumns = @JoinColumn(referencedColumnName = "id"))
	private List<NoteEntity> noteList = new ArrayList<NoteEntity>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public List<NoteEntity> getNoteList() {
		return noteList;
	}

	public void setNoteList(List<NoteEntity> noteList) {
		this.noteList = noteList;
	}
}
