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

import com.bridgelabz.fundoonotesapi.dto.ChangeLabelDto;
import com.bridgelabz.fundoonotesapi.dto.LabelDto;
import com.bridgelabz.fundoonotesapi.response.Response;
import com.bridgelabz.fundoonotesapi.services.ILabelService;

/**
 * @author Tejashree Surve 
 * @Purpose : This is RestApi Controller for Label Operation.
 */
@RestController
public class LabelController {

	@Autowired
	ILabelService labelservice;

	// Create New Label
	@PostMapping("/label/createlabel/{id}")
	public ResponseEntity<Response> createLabel(@RequestHeader String token, @PathVariable int id,
			@RequestBody LabelDto labeldto) {
		Response response = labelservice.addLabel(token, id, labeldto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Show All label
	@GetMapping("/label/getalllabel")
	public ResponseEntity<Response> getallLabel(@RequestHeader String token) {
		Response response = labelservice.getAlllabel(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Update label
	@PutMapping("/label/updatelabel/{id}")
	public ResponseEntity<Response> updateLabel(@RequestHeader String token, @PathVariable int id,
			@RequestBody LabelDto labeldto) {
		Response response = labelservice.updateLabel(token, id, labeldto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Delete label
	@DeleteMapping("/label/deletelabel/{id}")
	public ResponseEntity<Response> deleteLable(@RequestHeader String token, @PathVariable int id) {
		Response response = labelservice.deleteLabel(token, id);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/******************* Notes operation **********************/
	
	// Show Note By Label ID
	@GetMapping("/label/getNoteByLabelId/{id}")
	public ResponseEntity<Response> getNoteByLabelID(@PathVariable int id) {
		Response response = labelservice.getNoteByLabelId(id);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Show All Note By Label Name
	@GetMapping("/label/getNoteByLabelName")
	public ResponseEntity<Response> getNoteByLabelID(@RequestBody LabelDto labeldto) {
		Response response = labelservice.getNoteByLabelName(labeldto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/********************* Sort Label operation *********************/
	
	// Change Label By passing Id
	@PutMapping("/label/changelabel")
	public ResponseEntity<Response> changeLabel(@RequestBody ChangeLabelDto changelabel) {
		Response response = labelservice.changeLabel(changelabel);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	// Sort all Label By Name
	@GetMapping("/label/sortAllLabelByName")
	public ResponseEntity<Response> sortAllLabelByName() {
		Response response = labelservice.sortAllLabel();
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	// Sort user Label By Name
	@GetMapping("/label/sortLabelByName")
	public ResponseEntity<Response> sortLabelByName(@RequestHeader String token) {
		Response response = labelservice.sortLabelByTitle(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
