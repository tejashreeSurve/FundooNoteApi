package com.bridgelabz.fundoonotesapi.controller;

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
public class NoteController {

	@Autowired
	INoteService noteservice;

	@Autowired
	IReminderService reminderservice;

	// Create New Note
	@PostMapping("/notes/createnote")
	public ResponseEntity<Response> createNote(@RequestHeader String token, @RequestBody NoteDto notedto) {
		Response response = noteservice.createNote(token, notedto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Show All Note
	@GetMapping("/notes/getallnotes")
	public ResponseEntity<Response> getallNotes(@RequestHeader String token) {
		Response response = noteservice.getAllNotes(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Update Note
	@PutMapping("/notes/updatenote/{id}")
	public ResponseEntity<Response> updateNote(@RequestHeader String token, @PathVariable int id,
			@RequestBody NoteDto notedto) {
		Response response = noteservice.updateNote(token, id, notedto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Delete Note
	@DeleteMapping("/notes/deletenote/{id}")
	public ResponseEntity<Response> deleteNote(@RequestHeader String token, @PathVariable int id) {
		Response response = noteservice.deleteNote(token, id);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// archive or un-archive note by note id
	@PutMapping("/notes/archiveOrUnarchive/{id}")
	public ResponseEntity<Response> archiveOrUnarchive(@RequestHeader String token, @PathVariable int id) {
		Response response = noteservice.archiveOrUnarchive(token, id);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// pin or un-pin note by note id
	@PutMapping("/notes/pinOrUnpin/{id}")
	public ResponseEntity<Response> pinOrUnpin(@RequestHeader String token, @PathVariable int id) {
		Response response = noteservice.pinOrUnpin(token, id);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// trash or un-trash note by note id
	@PutMapping("/notes/trashOrUntrash/{id}")
	public ResponseEntity<Response> trashOrUntrash(@RequestHeader String token, @PathVariable int id) {
		Response response = noteservice.trashOrUntrash(token, id);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Sort User Note by Title
	@GetMapping("/notes/noteBytitle")
	public ResponseEntity<Response> sortByNotetitle(@RequestHeader String token) {
		Response response = noteservice.sortByNotetitle(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Sort User Note by Description
	@GetMapping("/notes/noteByDescription")
	public ResponseEntity<Response> sortByNoteDesc(@RequestHeader String token) {
		Response response = noteservice.sortByNoteDescription(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Sort User Note by Reminder Date
	@GetMapping("/notes/noteByReminderDate")
	public ResponseEntity<Response> sortBydate(@RequestHeader String token) {
		Response response = noteservice.sortByNoteDate(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Sort All Note by Title
	@GetMapping("/notes/allNoteByTitle")
	public ResponseEntity<Response> sortAllNoteByTitle() {
		Response response = noteservice.sortAllNoteBytitle();
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/******************************* Reminder Api's *******************************/

	// Add reminder to note
	@PostMapping("/reminder/addreminder/{noteid}")
	public ResponseEntity<Response> addReminder(@RequestHeader String token, @PathVariable int noteid,
			@RequestBody ReminderDto reminderDto) {
		Response response = reminderservice.addReminder(token, noteid, reminderDto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Show reminder
	@GetMapping("/reminder/getreminder")
	public ResponseEntity<Response> getReminder(@RequestHeader String token) {
		Response response = reminderservice.getReminder(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// update reminder
	@PutMapping("/reminder/updatereminder/{noteid}")
	public ResponseEntity<Response> updateReminder(@RequestHeader String token, @PathVariable int noteid,
			@RequestBody ReminderDto reminderDto) {
		Response response = reminderservice.updateReminder(token, noteid, reminderDto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Delete reminder from note
	@DeleteMapping("/reminder/deletereminder/{noteid}")
	public ResponseEntity<Response> deleteReminder(@RequestHeader String token, @PathVariable int noteid) {
		Response response = reminderservice.deleteReminder(token, noteid);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Set repeat Reminder to Daily option
	@PutMapping("/reminder/repeatDaily/{noteid}")
	public ResponseEntity<Response> repeatDaily(@RequestHeader String token, @PathVariable int noteid) {
		Response response = reminderservice.repeatDaily(token, noteid);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Set repeat Reminder to Weekly option
	@PutMapping("/reminder/repeatWeekly/{noteid}")
	public ResponseEntity<Response> repeatWeekly(@RequestHeader String token, @PathVariable int noteid) {
		Response response = reminderservice.repeatWeekly(token, noteid);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Set repeat Reminder to Monthly option
	@PutMapping("/reminder/repeatMonthly/{noteid}")
	public ResponseEntity<Response> repeatMonthly(@RequestHeader String token, @PathVariable int noteid) {
		Response response = reminderservice.repeatMonthly(token, noteid);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Set repeat Reminder to Yearly option
	@PutMapping("/reminder/repeatYearly/{noteid}")
	public ResponseEntity<Response> repeatYearly(@RequestHeader String token, @PathVariable int noteid) {
		Response response = reminderservice.repeatYearly(token, noteid);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
