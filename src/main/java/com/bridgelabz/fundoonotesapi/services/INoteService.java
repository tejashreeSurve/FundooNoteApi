
package com.bridgelabz.fundoonotesapi.services;

import com.bridgelabz.fundoonotesapi.dto.NoteDto;
import com.bridgelabz.fundoonotesapi.response.Response;
/**
 * @author Tejashree Surve
 * @Purpose : This is NoteService Interface which contains every method of services class.
 */
public interface INoteService {

	// create note
	Response createNote(String token,NoteDto notedto);
	
	// get all note
	Response getAllNotes(String token);
	
	// update note
	Response updateNote(String token,int id,NoteDto notedto);
	
	// delete note
	Response deleteNote(String token,int id);
	
	// pin or un-pin note
	Response pinOrUnpin(String token,int id);
	
	// archive or un-archive note
	Response archiveOrUnarchive(String token,int id);
	
	// trash or un-trash note
	Response trashOrUntrash(String token,int id);
	
	Response sortAllNoteBytitle();
	
	Response sortByNotetitle(String token);
	
	Response sortByNoteDate(String token);
	
}
