
package com.bridgelabz.fundoonotesapi.services;

import java.io.IOException;

import com.bridgelabz.fundoonotesapi.dto.NoteDto;
import com.bridgelabz.fundoonotesapi.response.Response;

/**
 * @author Tejashree Surve
 * @Purpose : This is NoteService Interface which contains every method of
 *          services class.
 */
public interface INoteService {

	// create note
	Response createNote(String token, NoteDto noteDto) throws Exception;

	// get all note
	Response getAllNotes(String token) throws Exception;

	// get note by label id
	Response getNoteByLabelId(String token, int labelId);

	// update note
	Response updateNote(String token, int noteId, NoteDto noteDto) throws IOException;

	// delete note
	Response deleteNote(String token, int noteId) throws IOException;

	// add label in note
	Response addLabelInNote(String token, int noteId, int labelId);

	// pin or un-pin note
	Response isPin(String token, int noteId);

	// archive or un-archive note
	Response isArchive(String token, int noteId);

	// trash or un-trash note
	Response isTrash(String token, int noteId);

	// sort user note by note title
	Response sortByNoteTitle(String token);

	// sort user note by note description
	Response sortByNoteDescription(String token);

}
