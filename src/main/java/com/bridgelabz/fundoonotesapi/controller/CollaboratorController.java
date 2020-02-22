package com.bridgelabz.fundoonotesapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotesapi.dto.CollaboratorDto;
import com.bridgelabz.fundoonotesapi.response.Response;
import com.bridgelabz.fundoonotesapi.services.ICollaboratorService;

/**
 * @author Tejashree Surve 
 * @Purpose : This is RestApi Controller for Collaborator Operation.
 */
@RestController
public class CollaboratorController {

	@Autowired
	ICollaboratorService collaboratorservice;
	
	// add Collaborator
	@PostMapping("/collaborator/addCollaborator/{noteid}")
	public ResponseEntity<Response> addCollaborator(@RequestHeader String token, @PathVariable int noteid,
			@RequestBody CollaboratorDto collaborator) {
		Response response = collaboratorservice.addCollaborator(token, noteid, collaborator);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	// Get all collaborator list
	@GetMapping("/collaborator/getCollaborator/{noteid}")
	public ResponseEntity<Response> getCollaborator(@RequestHeader String token, @PathVariable int noteid){
		Response response = collaboratorservice.getCollaborator(token, noteid);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	// Delete Collaborator
	@DeleteMapping("/collaborator/addCollaborator/{noteid}")
	public ResponseEntity<Response> deleteNote(@RequestHeader String token, @PathVariable int noteid,
			@RequestBody CollaboratorDto collaborator) {
		Response response = collaboratorservice.deleteCollaborator(token, noteid, collaborator);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
