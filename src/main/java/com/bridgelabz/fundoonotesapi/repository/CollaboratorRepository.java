package com.bridgelabz.fundoonotesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoonotesapi.model.CollaboratorEntity;

/**
 * @author Tejashree Surve 
 * @Purpose : This is CollaboratorRepository Interface which extends inbuilt JpaRepository.
 */
public interface CollaboratorRepository extends JpaRepository<CollaboratorEntity, Integer>{
	// find By Reciver Email	
	CollaboratorEntity findByMailReciver(String mailReciver);
}
