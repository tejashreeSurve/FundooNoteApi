package com.bridgelabz.fundoonotesapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotesapi.dto.NoteDto;
import com.bridgelabz.fundoonotesapi.response.Response;
import com.bridgelabz.fundoonotesapi.services.ElasticSearchNoteImp;

@RestController
@RequestMapping("/ElasticController")
public class ElasticSearchController {
	@Autowired
	ElasticSearchNoteImp elasticSearch;
	// Create New Note
		@PostMapping("/createNote")
		public ResponseEntity<Response> createNote(@RequestHeader String token, @RequestBody NoteDto notedto) throws IOException {
			Response response = elasticSearch.createNote(token, notedto);
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
		// Show All Note
		@GetMapping("/getAllNotes")
		public ResponseEntity<Response> getAllNotes() throws IOException {
			Response response = elasticSearch.getAllNotes();
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}

}
