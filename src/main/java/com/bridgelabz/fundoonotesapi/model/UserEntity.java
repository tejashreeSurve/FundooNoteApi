package com.bridgelabz.fundoonotesapi.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

/**
 * @author Tejashree Surve
 * @Purpose : This is Data Access Object class which is connect to DataBase
 *          Layer.
 */
@Component
@Entity
@Table(name = "userdetails")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String firstName;
	private String lastName;
	private String birthdate;
	private String phoneNumber;
	private String city;
	private String email;
	private String userPassword;
	private boolean isValidate = false;
	private String profilePic;

	@OneToMany(mappedBy = "userEntity")
	private List<NoteEntity> noteEntity;
	
	@OneToMany(mappedBy = "userEntity")
	private List<LabelEntity> labelEntity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getIsValidate() {
		return isValidate;
	}

	public void setIsValidate(boolean isValidate) {
		this.isValidate = isValidate;
	}

	public List<NoteEntity> getNoteEntity() {
		return noteEntity;
	}

	public void setNoteEntity(List<NoteEntity> noteEntity) {
		this.noteEntity = noteEntity;
	}

	public void setValidate(boolean isValidate) {
		this.isValidate = isValidate;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public List<LabelEntity> getLabelEntity() {
		return labelEntity;
	}

	public void setLabelEntity(List<LabelEntity> labelEntity) {
		this.labelEntity = labelEntity;
	}
}
