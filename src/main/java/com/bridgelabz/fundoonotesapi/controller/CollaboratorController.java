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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotesapi.dto.CollaboratorDto;
import com.bridgelabz.fundoonotesapi.response.Response;
import com.bridgelabz.fundoonotesapi.services.ICollaboratorService;

/**
 * @author Tejashree Surve
 * @Purpose : This is RestApi Controller for Collaborator Operation.
 */
@RestController
@RequestMapping("/collaborator")
public class CollaboratorController {

	@Autowired
	ICollaboratorService collaboratorService;

	// add Collaborator
	@PostMapping("/addCollaborator/{noteId}")
	public ResponseEntity<Response> addCollaborator(@RequestHeader String token, @PathVariable int noteId,
			@RequestBody CollaboratorDto collaborator) {
		Response response = collaboratorService.addCollaborator(token, noteId, collaborator);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Get all collaborator list
	@GetMapping("/getCollaborator/{noteId}")
	public ResponseEntity<Response> getCollaborator(@RequestHeader String token, @PathVariable int noteId) {
		Response response = collaboratorService.getCollaborator(token, noteId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Delete Collaborator
	@DeleteMapping("/deleteCollaborator/{noteId}/{collaboratorId}")
	public ResponseEntity<Response> deleteNote(@RequestHeader String token, @PathVariable int noteId,
			@PathVariable int collaboratorId) {
		Response response = collaboratorService.deleteCollaborator(token, noteId, collaboratorId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
