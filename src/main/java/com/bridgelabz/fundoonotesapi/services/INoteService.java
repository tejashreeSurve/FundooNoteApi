
package com.bridgelabz.fundoonotesapi.services;

import com.bridgelabz.fundoonotesapi.dto.NoteDto;
import com.bridgelabz.fundoonotesapi.response.Response;

/**
 * @author Tejashree Surve
 * @Purpose : This is NoteService Interface which contains every method of
 *          services class.
 */
public interface INoteService {

	// create note
	Response createNote(String token, NoteDto noteDto);

	// get all note
	Response getAllNotes(String token);

	// update note
	Response updateNote(String token, int noteId, NoteDto noteDto);
	
	// delete note
	Response deleteNote(String token, int noteId);

	// pin or un-pin note
	Response isPin(String token, int noteId);

	// archive or un-archive note
	Response isArchive(String token, int noteId);

	// trash or un-trash note
	Response isTrash(String token, int noteId);

	// sort all notes by title
	Response sortAllNoteByTitle();

	// sort user note by note title
	Response sortByNoteTitle(String token);
	
	// sort user note by note description
	Response sortByNoteDescription(String token);

}
