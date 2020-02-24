package com.bridgelabz.fundoonotesapi.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotesapi.dto.EmailForgetPasswordDto;
import com.bridgelabz.fundoonotesapi.dto.LoginUserDto;
import com.bridgelabz.fundoonotesapi.dto.ResetPasswordDto;
import com.bridgelabz.fundoonotesapi.dto.UserDto;
import com.bridgelabz.fundoonotesapi.exception.FileIsEmpty;
import com.bridgelabz.fundoonotesapi.exception.FileNotUploaded;
import com.bridgelabz.fundoonotesapi.exception.ForgetPasswordException;
import com.bridgelabz.fundoonotesapi.exception.LoginException;
import com.bridgelabz.fundoonotesapi.exception.RegistrationException;
import com.bridgelabz.fundoonotesapi.exception.ResetPasswordException;
import com.bridgelabz.fundoonotesapi.exception.ValidateException;
import com.bridgelabz.fundoonotesapi.message.MessageInfo;
import com.bridgelabz.fundoonotesapi.message.MessageResponse;
import com.bridgelabz.fundoonotesapi.model.UserEntity;
import com.bridgelabz.fundoonotesapi.repository.UserRepository;
import com.bridgelabz.fundoonotesapi.response.Response;
import com.bridgelabz.fundoonotesapi.utility.EmailSenderService;
import com.bridgelabz.fundoonotesapi.utility.JwtToken;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

/**
 * @author Tejashree Surve
 * @Purpose : This Class contains Logic for User for all RestApi methods.
 */
@Service
@PropertySource("message.properties")
public class UserServiceImp implements IUserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private JwtToken jwtobject;

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private MessageResponse messageResponse;

	private SimpleMailMessage email;

	@Autowired
	private Environment environment;

	@Autowired
	private MessageInfo message;

	// Login operation
	@Override
	public Response login(LoginUserDto loginUser) {
		UserEntity user = userRepository.findByEmail(loginUser.getEmail());
		String token = jwtobject.generateToken(loginUser.getEmail());
		System.out.println(token);
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		if (user.getIsValidate()) {
			if ((user.getUserpassword()).equals(loginUser.getUserpassword())) {
				return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
						environment.getProperty("status.login.success"), message.Login_Done);
			} else {
				return new Response(Integer.parseInt(environment.getProperty("status.redirect.code")),
						environment.getProperty("status.password.incorrect"), message.Invalide_Password);
			}
		} else {
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notverify"), message.User_Not_Verify);
		}
	}

	// For Verification
	@Override
	public Response validateUser(String token) {
		String email = jwtobject.getToken(token);
		UserEntity userIsVarified = userRepository.findByEmail(email);
		if (userIsVarified == null) {
			throw new ValidateException(message.User_Not_Exist);
		} else {
			userIsVarified.setIsValidate(true);
			userRepository.save(userIsVarified);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("status.email.isverify"), message.Verify_User);
		}
	}

	// Registration operation
	@Override
	public Response registration(UserDto user) {
		String emailIsPresent = user.getEmail();
		UserEntity userIsPresent = userRepository.findByEmail(emailIsPresent);
		if (userIsPresent != null) {
			throw new RegistrationException(message.User_Exist);
		}
		UserEntity userdata = mapper.map(user, UserEntity.class);
		userRepository.save(userdata);
		String token = jwtobject.generateToken(userdata.getEmail());
		System.out.println(token);
		email = messageResponse.verifyMail(userdata.getEmail(), userdata.getFirstname(), token);
		emailSenderService.sendEmail(email);

		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("status.user.register"), message.Registration_Done);
	}

	// Forget Password operation
	@Override
	public Response forgetPassword(EmailForgetPasswordDto emailForgetPassword) {
		UserEntity user = userRepository.findByEmail(emailForgetPassword.getEmail());
		if (user == null)
			throw new ForgetPasswordException(message.User_Exist);
		if (user.getIsValidate()) {
			String token = jwtobject.generateToken(emailForgetPassword.getEmail());
			UserEntity userdata = userRepository.findByEmail(emailForgetPassword.getEmail());
			System.out.println(token);
			email = messageResponse.passwordReset(emailForgetPassword.getEmail(), userdata.getFirstname(), token);
			emailSenderService.sendEmail(email);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("status.token.send"), message.Token_Send);
		} else {
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notverify"), message.User_Not_Verify);
		}
	}

	// Reset Password operation
	@Override
	public Response resetPassword(String token, ResetPasswordDto passwordreset) {
		String checkEmail = jwtobject.getToken(token);
		UserEntity userUpdate = userRepository.findByEmail(checkEmail);
		if (userUpdate == null)
			throw new ResetPasswordException(message.User_Exist);
		if (passwordreset.getConfirmpassword().equals(passwordreset.getPassword())) {
			userUpdate.setUserpassword(passwordreset.getPassword());
			userRepository.save(userUpdate);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("status.password.update"), message.Update_Password);
		} else {
			return new Response(Integer.parseInt(environment.getProperty("status.redirect.code")),
					environment.getProperty("status.password.incorrect"), message.Invalide_Password);
		}
	}

	// Get all User
	@Override
	public Response getAllUser() {
		List<UserEntity> listOfUser = userRepository.findAll();
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("status.user.display"), listOfUser);
	}

	// Sort User by Last-Name
	@Override
	public Response sortUserByLastName() {
		List<UserEntity> listOfUser = userRepository.findAll();
		List<UserEntity> sortedList = listOfUser.stream()
				.sorted((list1, list2) -> list1.getLastname().compareTo(list2.getLastname()))
				.collect(Collectors.toList());
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("status.user.display"), sortedList);
	}

	// Upload User Profile Pic
	@Override
	public Response uploadProfilePic(String token, MultipartFile file) {
		String email = jwtobject.getToken(token);
		// check whether user is present or not
		UserEntity user = userRepository.findByEmail(email);
		if (user == null) {
			throw new ValidateException(message.User_Not_Exist);
		}
		// file is not selected to upload 
		if (file.isEmpty())
			throw new FileIsEmpty(message.File_Is_Empty);
		
		File uploadFile = new File(file.getOriginalFilename());
		try {
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
			try {
				outputStream.write(file.getBytes());
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// set all cloudinary properties 
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "duquns9m9", "api_key",
				"691264649257271", "api_secret", "mRFRpme5AAqej5Ktef3GYVSzWtI"));
		Map<?, ?> uploadProfile;
		try {
			// this upload the image on cloudinary 
			uploadProfile = cloudinary.uploader().upload(uploadFile, ObjectUtils.emptyMap());
		} catch (IOException e) {
			throw new FileNotUploaded(message.File_Not_Upload);
		}
		// set the profile-pic url in userDetail table 
		user.setProfilePic(uploadProfile.get("secure_url").toString());
		userRepository.save(user);
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("upload.profilepic"), message.Profile_Uploaded);
	}
}
