package com.bridgelabz.fundoonotesapi.services;

import com.bridgelabz.fundoonotesapi.dto.CollaboratorDto;
import com.bridgelabz.fundoonotesapi.response.Response;

/**
 * @author Tejashree Surve
 * @Purpose : This is CollaboratorService Interface which contains every method of services class.
 */
public interface ICollaboratorService {

	// Add Collaborator
	Response addCollaborator(String token,int noteId,CollaboratorDto collaboratorDto);
	
	// Get all Collaborator of Note
	Response getCollaborator(String token,int noteId);
	
	// Delete Collaborator
	Response deleteCollaborator(String token ,int noteId,CollaboratorDto collaboratorDto);
}
