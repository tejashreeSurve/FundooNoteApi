//package com.bridgelabz.fundoonotesapi.controller;
//
//import java.io.IOException;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.bridgelabz.fundoonotesapi.dto.NoteDto;
//import com.bridgelabz.fundoonotesapi.model.NoteEntity;
//import com.bridgelabz.fundoonotesapi.services.ElasticSearchNoteImp;
//
//@RestController
//public class ElasticSearchController {
//	@Autowired
//	ElasticSearchNoteImp elasticSearchNote;
//	
//	// Create New Note
//		@PostMapping("/createNoteInElasticSearch")
//		public String createNote(@RequestHeader String token, @RequestBody NoteDto notedto) throws IOException {
//		return  elasticSearchNote.createNote(token, notedto);
//		}
//		// Show All Note
//		@GetMapping
//		public List<NoteEntity> getAllNotes() throws IOException {
//			System.out.println( elasticSearchNote.getAllNote());
//			return elasticSearchNote.getAllNote();
//		}
//		
//		@GetMapping("/{id}")
//		public NoteEntity getNote(String id) throws IOException {
//			return elasticSearchNote.getNote(id);
//		}
//		
//		@DeleteMapping("/{id}")
//		public String deleteResponse(String id) {
//			return elasticSearchNote.deleteNote(id);
//		}
//
//}
