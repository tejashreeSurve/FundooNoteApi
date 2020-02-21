package com.bridgelabz.fundoonotesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoonotesapi.model.LabelEntity;

/**
 * @author Tejashree Surve 
 * @Purpose : This is LabelRepository Interface which extends inbuilt JpaRepository.
 */
public interface LabelRepository extends JpaRepository<LabelEntity, Integer> {
	// find data by label id
	LabelEntity findById(int id);

	// find data by label name
	LabelEntity findByLabelname(String name);
}
