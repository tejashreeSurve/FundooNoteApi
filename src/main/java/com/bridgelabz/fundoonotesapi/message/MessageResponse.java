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
	public SimpleMailMessage verifyMail(String reciverEmail, String reciverName, String token) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(reciverEmail);
		message.setFrom("forgotbridge70@gmail.com");
		message.setSubject("Complete Verification!!!! ");
		message.setText("Hi, " + reciverName + " ,\n" + " Your email is verify with "
				+ " Token :- http://localhost:8083/userRegistration/validation/" + token);
		return message;
	}

	// send mail for forgot password
	public SimpleMailMessage passwordReset(String reciverEmail, String reciverName, String token) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(reciverEmail);
		message.setFrom("forgotbridge70@gmail.com");
		message.setSubject("Reset Password!!!! ");
		message.setText("Hi, " + reciverName + "  For reset Password, your token is " + "  Token :- " + token);
		return message;
	}
}
