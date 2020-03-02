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
	private JwtToken jwtObject;

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private MessageResponse messageResponse;

	SimpleMailMessage email;

	@Autowired
	private Environment environment;

	@Autowired
	private MessageInfo message;

	// Login operation
	@Override
	public Response login(LoginUserDto loginUser) {
		UserEntity user = userRepository.findByEmail(loginUser.getEmail());
		String token = jwtObject.generateToken(loginUser.getEmail());
		System.out.println(token);
		
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		
		if (user.getIsValidate()) {
			if ((user.getUserPassword()).equals(loginUser.getUserPassword())) {
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
		String email = jwtObject.getToken(token);
		UserEntity userIsVerified = userRepository.findByEmail(email);
		if (userIsVerified == null)
			throw new ValidateException(message.User_Not_Exist);
		userIsVerified.setIsValidate(true);
		userRepository.save(userIsVerified);
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("status.email.isverify"), message.Verify_User);
	}

	// Registration operation
	@Override
	public Response registration(UserDto user) {
		String checkEmail = user.getEmail();
		UserEntity userIsPresent = userRepository.findByEmail(checkEmail);
		if (userIsPresent != null) 
			throw new RegistrationException(message.User_Exist);
		UserEntity userData = mapper.map(user, UserEntity.class);
		String token = jwtObject.generateToken(userData.getEmail());
		System.out.println(token);
		email = messageResponse.verifyMail(userData.getEmail(), userData.getFirstName(), token);
		emailSenderService.sendEmail(email);
		userRepository.save(userData);
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("status.user.register"), message.Registration_Done);
	}

	// Forget Password operation
	@Override
	public Response forgetPassword(EmailForgetPasswordDto emailForgetPassword) {
		UserEntity user = userRepository.findByEmail(emailForgetPassword.getEmail());
		
		if (user == null)
			throw new ForgetPasswordException(message.User_Exist);
		
		// check whether user is done with validation or not
		if (user.getIsValidate()) {
			String token = jwtObject.generateToken(emailForgetPassword.getEmail());
			UserEntity userData = userRepository.findByEmail(emailForgetPassword.getEmail());
			System.out.println(token);
			email = messageResponse.passwordReset(emailForgetPassword.getEmail(), userData.getFirstName(), token);
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
	public Response resetPassword(String token, ResetPasswordDto passwordReset) {
		String checkEmail = jwtObject.getToken(token);
		UserEntity userUpdate = userRepository.findByEmail(checkEmail);
		
		if (userUpdate == null)
			throw new ResetPasswordException(message.User_Exist);
		
		if (passwordReset.getConfirmPassword().equals(passwordReset.getPassword())) {
			userUpdate.setUserPassword(passwordReset.getPassword());
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
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("status.user.display"), userRepository.findAll());
	}

	// Sort User by Last-Name
	@Override
	public Response sortUserByLastName() {
		List<UserEntity> sortedList = userRepository.findAll().stream()
				.sorted((list1, list2) -> list1.getLastName().compareTo(list2.getLastName()))
				.collect(Collectors.toList());
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("status.user.display"), sortedList);
	}

	// Upload User Profile Pic
	@Override
	public Response uploadProfilePic(String token, MultipartFile file) {
		String email = jwtObject.getToken(token);
		// check whether user is present or not
		UserEntity user = userRepository.findByEmail(email);
		if (user == null) 
			throw new ValidateException(message.User_Not_Exist);
		
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
