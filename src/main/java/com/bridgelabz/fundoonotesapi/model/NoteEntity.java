package com.bridgelabz.fundoonotesapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Tejashree Surve 
 * @Purpose : This is Entity Class for Note DataBase.
 */
@Entity
@Component
@Table(name = "notes")
@JsonIgnoreProperties({ "userEntity", "labelList" })
public class NoteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NonNull
	private String title;
	@NonNull
	private String description;
	@NonNull
	private boolean archiveOrUnArchive;
	@NonNull
	private boolean pinOrUnPin;
	@NonNull
	private boolean trashOrUnTrash;
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity userEntity;
	@ManyToMany
	private List<LabelEntity> labelList = new ArrayList<LabelEntity>();
	
	@OneToOne(mappedBy = "noteEntity")
	private ReminderEntity reminderEntity;
	
	@OneToMany(mappedBy = "noteEntity")
	private List<CollaboratorEntity> collaboratorList = new ArrayList<CollaboratorEntity>();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public boolean isArchiveOrUnArchive() {
		return archiveOrUnArchive;
	}

	public void setArchiveOrUnArchive(boolean archiveOrUnArchive) {
		this.archiveOrUnArchive = archiveOrUnArchive;
	}

	public boolean isPinOrUnPin() {
		return pinOrUnPin;
	}

	public void setPinOrUnPin(boolean pinOrUnPin) {
		this.pinOrUnPin = pinOrUnPin;
	}

	public boolean isTrashOrUnTrash() {
		return trashOrUnTrash;
	}

	public void setTrashOrUnTrash(boolean trashOrUnTrash) {
		this.trashOrUnTrash = trashOrUnTrash;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity UserEntity) {
		this.userEntity = UserEntity;
	}

	public List<LabelEntity> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<LabelEntity> labelList) {
		this.labelList = labelList;
	}

	public ReminderEntity getReminderEntity() {
		return reminderEntity;
	}

	public void setReminderEntity(ReminderEntity reminderEntity) {
		this.reminderEntity = reminderEntity;
	}

	public List<CollaboratorEntity> getCollaboratorList() {
		return collaboratorList;
	}

	public void setCollaboratorList(List<CollaboratorEntity> collaboratorList) {
		this.collaboratorList = collaboratorList;
	}
}
