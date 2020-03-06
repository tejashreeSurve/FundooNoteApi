package com.bridgelabz.fundoonotesapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotesapi.dto.NoteDto;
import com.bridgelabz.fundoonotesapi.dto.ReminderDto;
import com.bridgelabz.fundoonotesapi.response.Response;
import com.bridgelabz.fundoonotesapi.services.INoteService;
import com.bridgelabz.fundoonotesapi.services.IReminderService;

/**
 * @author Tejashree Surve
 * @Purpose : This is RestApi Controller for Notes Operation.
 */
@RestController
@RequestMapping("/notes")
public class NoteController {

	@Autowired
	INoteService noteService;

	@Autowired
	IReminderService reminderService;

	// Create New Note
	@PostMapping("/createNote")
	public ResponseEntity<Response> createNote(@RequestHeader String token, @RequestBody NoteDto notedto) {
		Response response = noteService.createNote(token, notedto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Create New Note
	@PostMapping("/addLabelInNote/{noteId}/{labelId}")
	public ResponseEntity<Response> addLabelInNote(@RequestHeader String token, @PathVariable int noteId,
			@PathVariable int labelId) {
		Response response = noteService.addLabelInNote(token, noteId, labelId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Show All Note
	@GetMapping("/getAllNotes")
	public ResponseEntity<Response> getAllNotes(@RequestHeader String token) {
		Response response = noteService.getAllNotes(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Show All Note by LabelId
	@GetMapping("/getNoteByLabelId/{labelId}")
	public ResponseEntity<Response> getNoteByLabelId(@RequestHeader String token, @PathVariable int labelId) {
		Response response = noteService.getNoteByLabelId(token, labelId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Update Note
	@PutMapping("/updateNote/{noteId}")
	public ResponseEntity<Response> updateNote(@RequestHeader String token, @PathVariable int noteId,
			@RequestBody NoteDto notedto) {
		Response response = noteService.updateNote(token, noteId, notedto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Delete Note
	@DeleteMapping("/deleteNote/{noteId}")
	public ResponseEntity<Response> deleteNote(@RequestHeader String token, @PathVariable int noteId) {
		Response response = noteService.deleteNote(token, noteId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// archive or un-archive note by note id
	@PutMapping("/isArchive/{noteId}")
	public ResponseEntity<Response> isArchive(@RequestHeader String token, @PathVariable int noteId) {
		Response response = noteService.isArchive(token, noteId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// pin or un-pin note by note id
	@PutMapping("/isPin/{noteId}")
	public ResponseEntity<Response> isPin(@RequestHeader String token, @PathVariable int noteId) {
		Response response = noteService.isPin(token, noteId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// trash or un-trash note by note id
	@PutMapping("/isTrash/{noteId}")
	public ResponseEntity<Response> isTrash(@RequestHeader String token, @PathVariable int noteId) {
		Response response = noteService.isTrash(token, noteId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Sort User Note by Title
	@GetMapping("/noteByTitle")
	public ResponseEntity<Response> sortByNoteTitle(@RequestHeader String token) {
		Response response = noteService.sortByNoteTitle(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Sort User Note by Description
	@GetMapping("/noteByDescription")
	public ResponseEntity<Response> sortByNoteDesc(@RequestHeader String token) {
		Response response = noteService.sortByNoteDescription(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/******************************* Reminder Api's *******************************/

	// Add reminder to note
	@PostMapping("/reminder/addReminder/{noteId}")
	public ResponseEntity<Response> addReminder(@RequestHeader String token, @PathVariable int noteId,
		@Valid	@RequestBody ReminderDto reminderDto) {
		Response response = reminderService.addReminder(token, noteId, reminderDto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Show reminder
	@GetMapping("/reminder/getReminder")
	public ResponseEntity<Response> getReminder(@RequestHeader String token) {
		Response response = reminderService.getReminder(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// update reminder
	@PutMapping("/reminder/updateReminder/{noteId}")
	public ResponseEntity<Response> updateReminder(@RequestHeader String token, @PathVariable int noteId,
			@RequestBody ReminderDto reminderDto) {
		Response response = reminderService.updateReminder(token, noteId, reminderDto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Delete reminder from note
	@DeleteMapping("/reminder/deleteReminder/{noteId}")
	public ResponseEntity<Response> deleteReminder(@RequestHeader String token, @PathVariable int noteId) {
		Response response = reminderService.deleteReminder(token, noteId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Set repeat Reminder to Daily option
	@PutMapping("/reminder/repeatDaily/{noteId}")
	public ResponseEntity<Response> repeatDaily(@RequestHeader String token, @PathVariable int noteId) {
		Response response = reminderService.repeatDaily(token, noteId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Set repeat Reminder to Weekly option
	@PutMapping("/reminder/repeatWeekly/{noteId}")
	public ResponseEntity<Response> repeatWeekly(@RequestHeader String token, @PathVariable int noteId) {
		Response response = reminderService.repeatWeekly(token, noteId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Set repeat Reminder to Monthly option
	@PutMapping("/reminder/repeatMonthly/{noteId}")
	public ResponseEntity<Response> repeatMonthly(@RequestHeader String token, @PathVariable int noteId) {
		Response response = reminderService.repeatMonthly(token, noteId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Set repeat Reminder to Yearly option
	@PutMapping("/reminder/repeatYearly/{noteId}")
	public ResponseEntity<Response> repeatYearly(@RequestHeader String token, @PathVariable int noteId) {
		Response response = reminderService.repeatYearly(token, noteId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
