package com.bridgelabz.fundoonotesapi.services;


import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotesapi.dto.EmailForgetPasswordDto;
import com.bridgelabz.fundoonotesapi.dto.LoginUserDto;
import com.bridgelabz.fundoonotesapi.dto.ResetPasswordDto;
import com.bridgelabz.fundoonotesapi.dto.UserDto;
import com.bridgelabz.fundoonotesapi.response.Response;

/**
 * @author Tejashree Surve
 * @Purpose : Interface of UserServices methods.
 */
public interface IUserService {
	// login
	Response login(LoginUserDto loginUser);

	// registration
	Response registration(UserDto user);

	// forget password
	Response forgetPassword(EmailForgetPasswordDto email);

	// reset password
	Response resetPassword(String token, ResetPasswordDto passwordRest);
	
	// validate user
	Response validateUser(String token);
	
	// get all user
	Response getAllUser();
	
	// sort user by last-name
	Response sortUserByLastName();
	
	// upload profile pic 
	Response uploadProfilePic(String token,MultipartFile file);
}
