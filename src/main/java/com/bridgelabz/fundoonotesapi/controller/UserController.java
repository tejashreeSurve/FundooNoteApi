package com.bridgelabz.fundoonotesapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotesapi.dto.EmailForgetPasswordDto;
import com.bridgelabz.fundoonotesapi.dto.LoginUserDto;
import com.bridgelabz.fundoonotesapi.dto.ResetPasswordDto;
import com.bridgelabz.fundoonotesapi.dto.UserDto;
import com.bridgelabz.fundoonotesapi.response.Response;
import com.bridgelabz.fundoonotesapi.services.IUserService;

/**
 * @author Tejashree Surve
 * @Purpose : This is RestApi Controller for User Operation.
 */
@RestController
@RequestMapping("/userRegistration")
public class UserController {

	@Autowired
	private IUserService userService;

	// Register RestApi method
	@PostMapping("/register")
	public ResponseEntity<Response> registrationUser(@Valid @RequestBody UserDto user) {
		Response response = userService.registration(user);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Login RestApi method
	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginUserDto loginUser) {
		Response response = userService.login(loginUser);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Get All User
	@GetMapping("/getAllUser")
	public ResponseEntity<Response> getAllUser() {
		Response response = userService.getAllUser();
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Forget password RestApi method
	@PostMapping("/forgetPassword")
	public ResponseEntity<Response> forgetPassword(@RequestBody EmailForgetPasswordDto emailForgetPassword) {
		Response response = userService.forgetPassword(emailForgetPassword);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Reset Password RestApi method
	@PutMapping("/resetPassword")
	public ResponseEntity<Response> resetPassword(@RequestHeader String token, @RequestBody ResetPasswordDto password) {
		Response response = userService.resetPassword(token, password);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// User Validation RestApi method
	@GetMapping("/validation/{token}")
	public ResponseEntity<String> validation(@PathVariable String token) {
		Response response = userService.validateUser(token);
		return new ResponseEntity<String>(response.getMessage(), HttpStatus.OK);
	}

	// Sort All User by Last-Name
	@GetMapping("/sortByUserLastName")
	public ResponseEntity<Response> sortByLastName() {
		Response response = userService.sortUserByLastName();
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	// Upload User Profile Pic
	@PostMapping("/upload")
	public ResponseEntity<Response> upload(@RequestHeader String token, @RequestHeader MultipartFile file) {
		Response response = userService.uploadProfilePic(token, file);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
