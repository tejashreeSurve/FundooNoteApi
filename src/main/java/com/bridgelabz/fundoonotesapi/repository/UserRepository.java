package com.bridgelabz.fundoonotesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoonotesapi.model.UserEntity;

/**
 * @author Tejashree Surve
 * @Purpose : This is UserRepository which access the data from DataBase.
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	// find data by user email
	UserEntity findByEmail(String email);
}
