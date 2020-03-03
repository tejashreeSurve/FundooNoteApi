package com.bridgelabz.fundoonotesapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotesapi.dto.ChangeLabelDto;
import com.bridgelabz.fundoonotesapi.dto.LabelDto;
import com.bridgelabz.fundoonotesapi.message.MessageInfo;
import com.bridgelabz.fundoonotesapi.model.LabelEntity;
import com.bridgelabz.fundoonotesapi.model.NoteEntity;
import com.bridgelabz.fundoonotesapi.model.UserEntity;
import com.bridgelabz.fundoonotesapi.repository.LabelRepository;
import com.bridgelabz.fundoonotesapi.repository.NoteRepository;
import com.bridgelabz.fundoonotesapi.repository.UserRepository;
import com.bridgelabz.fundoonotesapi.response.Response;
import com.bridgelabz.fundoonotesapi.utility.JwtToken;

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

	// Add Label
	@Override
	public Response createLabel(String token, LabelDto labelDto) {
		String email = jwtToken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);

		LabelEntity labelEntity = mapper.map(labelDto, LabelEntity.class);
		labelEntity.setUserEntity(user);
		labelRepository.save(labelEntity);
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("label.create"), message.Label_Create);
	}

	// Get all Label of User
	@Override
	public Response getAllLabel(String token) {
		String email = jwtToken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		if(user.getLabelEntity() == null)
			return new Response(Integer.parseInt(environment.getProperty("status.redirect.code")),
					environment.getProperty("label.notexist"), message.Label_Not_Exist);
		
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("label.getAllLabels"), user.getLabelEntity());
	}

	// Update Label
	@Override
	public Response editLabel(String token,int labelId, LabelDto labelDto) {
		String email = jwtToken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);

		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);

		LabelEntity labelData = labelRepository.findById(labelId);
		if (labelRepository.findById(labelId) == null)
			return new Response(Integer.parseInt(environment.getProperty("status.redirect.code")),
					environment.getProperty("label.notexist"), message.Label_Not_Exist);

		labelData.setLabelName(labelDto.getLabelName());
		labelRepository.save(labelData);
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("label.update"), message.Label_Update);
	}

	// Delete Label
	@Override
	public Response deleteLabel(String token, int labelId) {
		String email = jwtToken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);

		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);

		if (labelRepository.findById(labelId) == null)
			return new Response(Integer.parseInt(environment.getProperty("status.redirect.code")),
					environment.getProperty("label.notexist"), message.Label_Not_Exist);

		labelRepository.deleteById(labelId);
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("label.delete"), message.Label_Delete);
	}

	// Change Label by Label ID's
	@Override
	public Response changeLabel(ChangeLabelDto changeLabel) {
		LabelEntity labelOldData = labelRepository.findById(changeLabel.getPreLabelId());
		LabelEntity labelChangeData = labelRepository.findById(changeLabel.getChangeLabelId());
		if (labelOldData == null || labelChangeData == null) {
			return new Response(Integer.parseInt(environment.getProperty("status.redirect.code")),
					environment.getProperty("label.notexist"), message.Label_Not_Exist);
		}
		labelChangeData.setLabelName(labelOldData.getLabelName());
		labelRepository.save(labelChangeData);
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("label.change"), message.Label_Change);
	}
	
	// Get Notes where Label is present by Id
	@Override
	public Response getNoteByLabelName(String token, int labelId) {
		String email = jwtToken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);

		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);

		if (labelRepository.findById(labelId) == null)
			return new Response(Integer.parseInt(environment.getProperty("status.redirect.code")),
					environment.getProperty("label.notexist"), message.Label_Not_Exist);

		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("note.getallnotes"), labelRepository.findById(labelId).getNoteList());
	}

	// Sort Label by Title
	@Override
	public Response sortLabelByTitle(String token) {
		String email = jwtToken.getToken(token);
		// Optional<UserEntity> user = Optional.of(userRepository.findByEmail(email));
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		// sort list by label name
		List<LabelEntity> sortedList = user.getLabelEntity().stream()
				.sorted((list1, list2) -> list1.getLabelName().compareTo(list2.getLabelName()))
				.collect(Collectors.toList());
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("label.getAllLabels"), sortedList);
	}
}
