package com.bridgelabz.fundoonotesapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotesapi.dto.NoteDto;
import com.bridgelabz.fundoonotesapi.response.Response;

@RestController
@RequestMapping("/elasticSearch")
public class ElasticSearchNoteController {
	
	
	// Create New Note
		@PostMapping("/createNote")
		public ResponseEntity<Response> createNote( @RequestBody NoteDto notedto) {
			Response response = noteService.createNote( notedto);
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
}
