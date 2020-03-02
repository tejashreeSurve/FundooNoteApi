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
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/label")
public class LabelController {

	@Autowired
	ILabelService labelService;

	// Create New Label
	@PostMapping("/createLabel/{id}")
	public ResponseEntity<Response> createLabel(@RequestHeader String token, @PathVariable int id,
			@RequestBody LabelDto labeldto) {
		Response response = labelService.addLabel(token, id, labeldto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Show All label
	@GetMapping("/getAllLabel")
	public ResponseEntity<Response> getAllLabel(@RequestHeader String token) {
		Response response = labelService.getAllLabel(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Update label
	@PutMapping("/updateLabel/{labelId}")
	public ResponseEntity<Response> updateLabel(@RequestHeader String token, @PathVariable int labelId,
			@RequestBody LabelDto labeldto) {
		Response response = labelService.updateLabel(token, labelId, labeldto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Delete label
	@DeleteMapping("/deleteLabel/{labelId}")
	public ResponseEntity<Response> deleteLable(@RequestHeader String token, @PathVariable int labelId) {
		Response response = labelService.deleteLabel(token, labelId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/******************* Notes operation **********************/

	// Show Note By Label Name
	@GetMapping("/getNoteByLabelName/{labelId}")
	public ResponseEntity<Response> getNoteByLabelName(@RequestHeader String token,@PathVariable int labelId) {
		Response response = labelService.getNoteByLabelName(token,labelId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/********************* Sort Label operation *********************/

	// Change Label By passing Id
	@PutMapping("/changeLabel")
	public ResponseEntity<Response> changeLabel(@RequestBody ChangeLabelDto changelabel) {
		Response response = labelService.changeLabel(changelabel);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Sort all Label By Name
	@GetMapping("/sortAllLabelByName")
	public ResponseEntity<Response> sortAllLabelByName() {
		Response response = labelService.sortAllLabel();
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Sort user Label By Name
	@GetMapping("/sortLabelByName")
	public ResponseEntity<Response> sortLabelByName(@RequestHeader String token) {
		Response response = labelService.sortLabelByTitle(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
