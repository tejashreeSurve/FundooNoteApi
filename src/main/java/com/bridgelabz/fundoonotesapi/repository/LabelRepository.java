package com.bridgelabz.fundoonotesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoonotesapi.model.LabelEntity;

/**
 * @author Tejashree Surve
 * @Purpose : This is LabelRepository Interface which extends inbuilt
 *          JpaRepository.
 */
public interface LabelRepository extends JpaRepository<LabelEntity, Integer> {
	// find data by label name
	LabelEntity findByLabelName(String labelName);
}
