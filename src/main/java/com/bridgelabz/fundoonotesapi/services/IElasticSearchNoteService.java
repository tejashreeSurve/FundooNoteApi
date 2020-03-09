package com.bridgelabz.fundoonotesapi.services;

import java.io.IOException;

import com.bridgelabz.fundoonotesapi.dto.NoteDto;
import com.bridgelabz.fundoonotesapi.response.Response;

public interface IElasticSearchNoteService {

	// create note
	Response createNote(String token, NoteDto noteDto)throws IOException ;
	// get all note
	Response getAllNotes()throws IOException;
}
