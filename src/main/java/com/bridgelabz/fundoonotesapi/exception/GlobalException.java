package com.bridgelabz.fundoonotesapi.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.fundoonotesapi.message.MessageInfo;
import com.bridgelabz.fundoonotesapi.response.Response;
/**
 * @author Tejashree Surve
 * @Purpose : This is Global Exception.
 */
@ControllerAdvice
public class GlobalException {
	Environment environment;
	@Autowired
	MessageInfo message;

	@ExceptionHandler(ForgetPasswordException.class)
	public ResponseEntity<Response> ForgetPasswordException(Exception e) {
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request),
				e.getMessage(), "Please try again!!!"), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LoginException.class)
	public ResponseEntity<Response> LoginException(Exception e) {
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request),
				e.getMessage(), "Please try again!!!"), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RegistrationException.class)
	public ResponseEntity<Response> RegistrationException(Exception e) {
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request),
				e.getMessage(), "Please try again!!!"), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResetPasswordException.class)
	public ResponseEntity<Response> ResetPasswordException(Exception e) {
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request),
				e.getMessage(), "Please try again!!!"), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ValidateException.class)
	public ResponseEntity<Response> ValidateException(Exception e) {
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request),
				e.getMessage(), "Please try again!!!"), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalideTokenException.class)
	public ResponseEntity<Response> InvalideTokenException(Exception e) {
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request),
				e.getMessage(), "Please try again!!!"), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ReminderNotPresentException.class)
	public ResponseEntity<Response> ReminderNotPresentException(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request),
				e.getMessage(), "Please try again!!!"), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FileIsEmpty.class)
	public ResponseEntity<Response> FileIsEmpty(Exception e){
		return new ResponseEntity<Response>(new Response (Integer.parseInt(message.Bad_Request),
				e.getMessage(),"Please try again!!!"),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FileNotUploaded.class)
	public ResponseEntity<Response> FileNotUploaded(Exception e){
		return new ResponseEntity<Response>(new Response (Integer.parseInt(message.Bad_Request),
				e.getMessage(),"Please try again!!!"),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ReciverEmailofCollaborator.class)
	public ResponseEntity<Response> ReciverEmailofCollaborator(Exception e){
		return new ResponseEntity<Response>(new Response (Integer.parseInt(message.Bad_Request),
				e.getMessage(),"Please try again!!!"),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoteNotFoundException.class)
	public ResponseEntity<Response> NoteNotFoundException(Exception e){
		return new ResponseEntity<Response>(new Response (Integer.parseInt(message.Bad_Request),
				e.getMessage(),"Please try again!!!"),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(LabelNotExistException.class)
	public ResponseEntity<Response> LabelNotExistException(Exception e){
		return new ResponseEntity<Response>(new Response (Integer.parseInt(message.Bad_Request),
				e.getMessage(),"Please try again!!!"),HttpStatus.BAD_REQUEST);
	}
}
