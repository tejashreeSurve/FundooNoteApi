package com.bridgelabz.fundoonotesapi.services;

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
	Response resetPassword(String token, ResetPasswordDto passwordrest);
	
	// validate user
	Response validateUser(String token);
}
