package com.bridgelabz.fundoonotesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoonotesapi.model.NoteEntity;
import com.bridgelabz.fundoonotesapi.model.UserEntity;

/**
 * @author Tejashree Surve 
 * @Purpose : This is NoteRepository Interface which extends inbuilt JpaRepository.
 */
public interface NoteRepository extends JpaRepository<NoteEntity, Integer> {
	// find data by Note title
	NoteEntity findByTitle(String title);

	// find data by Note Id
	NoteEntity findById(int id);

	// find data by user
	NoteEntity findByUserEntity(UserEntity userdata);

}
