package com.bridgelabz.fundoonotesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoonotesapi.model.ReminderEntity;
/**
 * @author Tejashree Surve 
 * @Purpose : This is ReminderRepository Interface which extends inbuilt JpaRepository.
 */
public interface ReminderRepository extends JpaRepository<ReminderEntity, Integer> {

}
