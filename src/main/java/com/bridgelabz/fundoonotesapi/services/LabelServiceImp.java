package com.bridgelabz.fundoonotesapi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotesapi.dto.LabelDto;
import com.bridgelabz.fundoonotesapi.exception.LabelNotExistException;
import com.bridgelabz.fundoonotesapi.exception.LoginException;
import com.bridgelabz.fundoonotesapi.message.MessageInfo;
import com.bridgelabz.fundoonotesapi.model.LabelEntity;
import com.bridgelabz.fundoonotesapi.model.UserEntity;
import com.bridgelabz.fundoonotesapi.repository.LabelRepository;
import com.bridgelabz.fundoonotesapi.repository.NoteRepository;
import com.bridgelabz.fundoonotesapi.repository.UserRepository;
import com.bridgelabz.fundoonotesapi.response.Response;
import com.bridgelabz.fundoonotesapi.utility.JwtToken;
import com.sun.istack.logging.Logger;

/**
 * @author Tejashree Surve
 * @Purpose : This is Service class for Label which contain logic for all Api's.
 */
@Component
@Service
@PropertySource("message.properties")
public class LabelServiceImp implements ILabelService {

	@Autowired
	JwtToken jwtToken;

	@Autowired
	UserRepository userRepository;

	@Autowired
	Environment environment;

	@Autowired
	ModelMapper mapper;

	@Autowired
	LabelRepository labelRepository;

	@Autowired
	NoteRepository noteRepository;

	@Autowired
	MessageInfo message;
	
	private static final Logger LOGGER = Logger.getLogger(NoteServiceImp.class);

	// Add Label
	@Override
	public Response createLabel(String token, LabelDto labelDto) {
		String email = jwtToken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		LabelEntity labelEntity = mapper.map(labelDto, LabelEntity.class);
		labelEntity.setUserEntity(user);
		labelRepository.save(labelEntity);
		LOGGER.info("User is successfully inserted into table");
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("label.create"), message.Label_Create);
	}

	// Get all Label of User
	@Override
	public Response getAllLabel(String token) {
		String email = jwtToken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		// check if label is present or not
		if(user.getLabelList() == null)
			throw new LabelNotExistException(message.Label_Not_Exist);
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("label.getAllLabels"), user.getLabelList());
	}

	// Update Label
	@Override
	public Response editLabel(String token,int labelId, LabelDto labelDto) {
		String email = jwtToken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		// check if label is present or not
		LabelEntity labelData = labelRepository.findById(labelId).orElseThrow(() ->new LabelNotExistException(message.Label_Not_Exist));
		labelData.setLabelName(labelDto.getLabelName());
		labelRepository.save(labelData);
		LOGGER.info("label is successfully updated and save into table");
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("label.update"), message.Label_Update);
	}

	// Delete Label
	@Override
	public Response deleteLabel(String token, int labelId) {
		String email = jwtToken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		// check if label is present or not
		if (labelRepository.findById(labelId) == null)
			throw new LabelNotExistException(message.Label_Not_Exist);
		labelRepository.deleteById(labelId);
		LOGGER.info("label is successfully deleted from table");
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("label.delete"), message.Label_Delete);
	}

	// Get Notes where Label is present by Id
	@Override
	public Response getNoteByLabelName(String token, int labelId) {
		String email = jwtToken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		// check if label is present or not
		LabelEntity labelEntity = labelRepository.findById(labelId).orElseThrow(() -> new LabelNotExistException(message.Label_Not_Exist));
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("note.getallnotes"), labelEntity.getNoteList());
	}

	// Sort Label by Title
	@Override
	public Response sortLabelByTitle(String token) {
		String email = jwtToken.getToken(token);
		// Optional<UserEntity> user = Optional.of(userRepository.findByEmail(email));
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		// sort list by label name
		List<LabelEntity> sortedList = user.getLabelList().stream()
				.sorted((list1, list2) -> list1.getLabelName().compareTo(list2.getLabelName()))
				.collect(Collectors.toList());
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("label.getAllLabels"), sortedList);
	}
}
