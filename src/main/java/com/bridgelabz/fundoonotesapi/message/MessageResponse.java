package com.bridgelabz.fundoonotesapi.message;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;


/**
 * @author Tejashree Surve
 * @Purpose : This class contains mail information which is send to user.
 */
@Component
public class MessageResponse {
	// send mail for verification 
	public SimpleMailMessage verifyMail(String reciveremail, String recivername, String token) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(reciveremail);
		message.setFrom("forgotbridge70@gmail.com");
		message.setSubject("Complete Verification!!!! ");
		message.setText("Hi, " + recivername + "  Your email is verify with " + " Token :- " + token);
		return message;
	}

	// send mail for forgot password
	public SimpleMailMessage passwordReset(String reciveremail, String recivername, String token) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(reciveremail);
		message.setFrom("forgotbridge70@gmail.com");
		message.setSubject("Reset Password!!!! ");
		message.setText("Hi, " + recivername + "  For reset Password, your token is " + "  Token :- " + token);
		return message;
	}
}
