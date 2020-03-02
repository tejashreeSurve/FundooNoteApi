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
		
//			// setting list of notes in LabelEntity to list
			LabelEntity labelEntity = mapper.map(labelDto, LabelEntity.class);
//			List<NoteEntity> noteList = labelEntity.getNoteList();
//			noteList.add(noteData);
//			labelEntity.setNoteList(noteList);
//			// setting the list of label in NoteEntity to list
//			List<LabelEntity> labelList = noteData.getLabelList();
//			labelList.add(labelEntity);
//			noteData.setLabelList(labelList);

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
//		List<NoteEntity> noteList = noteRepository.findAll();
//		// this list contains all notes which is related to user
//		List<NoteEntity> listOfNotes = noteList.stream()
//				.filter(userData -> userData.getUserEntity().getId() == user.getId()).collect(Collectors.toList());
//		List<LabelEntity> labelList = new ArrayList<LabelEntity>();
//		// this for loop get all label present in that notes list
//		for (int i = 0; i < listOfNotes.size(); i++) {
//			for (int j = 0; j < listOfNotes.get(i).getLabelList().size(); j++) {
//				labelList.add(listOfNotes.get(i).getLabelList().get(j));
//			}
//		}
		
		List<LabelEntity> labelList = new ArrayList<LabelEntity>();
		for (NoteEntity noteEntity : user.getNoteEntity()) {
		for(LabelEntity labelEntity : noteEntity.getLabelList()) {	
			labelList.add(labelEntity);
		}
		}
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("label.getAllLabels"), labelList);
	}

	// Update Label
	@Override
	public Response updateLabel(String token, int id, LabelDto labelDto) {
		String email = jwtToken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		
		LabelEntity labelData = labelRepository.findById(id);
		if (labelRepository.findById(id) == null)
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

	// Get Notes where Label is present by Id
	@Override
	public Response getNoteByLabelName(String token,int labelId) {
		String email = jwtToken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		
		if (labelRepository.findById(labelId) == null)
			return new Response(Integer.parseInt(environment.getProperty("status.redirect.code")),
			environment.getProperty("label.notexist"), message.Label_Not_Exist);
//		if (labelData == null)
//			return new Response(Integer.parseInt(environment.getProperty("status.redirect.code")),
//					environment.getProperty("label.notexist"), message.Label_Not_Exist);
//		List<NoteEntity> notelist = noteRepository.findAll();
//		List<LabelEntity> notes = new ArrayList<LabelEntity>();
//		// this for loop will get all notes where label id == id
//		for (int i = 0; i < notelist.size(); i++) {
//			for (int j = 0; j < notelist.get(i).getLabelList().size(); j++) {
//				if (notelist.get(i).getLabelList().get(j).getId() == labelid)
//					notes.add(notelist.get(i).getLabelList().get(j));
//			}
//		}
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("note.getallnotes"), labelRepository.findById(labelId).getNoteList());
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

	// Sort Label by Title
	@Override
	public Response sortLabelByTitle(String token) {
		String email = jwtToken.getToken(token);
		//Optional<UserEntity> user = Optional.of(userRepository.findByEmail(email));
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
//		List<NoteEntity> noteList = noteRepository.findAll();
//		// this list contains all notes which is related to user
//		List<NoteEntity> listOfNotes = noteList.stream()
//				.filter(userData -> userData.getUserEntity().getId() == user.getId()).collect(Collectors.toList());
		
		List<LabelEntity> labelList = new ArrayList<LabelEntity>();
		// this is provide label list
		for (NoteEntity noteEntity :  user.getNoteEntity()) {
			for (LabelEntity label : noteEntity.getLabelList()) {
				labelList.add(label);
			}
		}
		// sort list by label name
		List<LabelEntity> sortedList = labelList.stream()
		.sorted((list1, list2) -> list1.getLabelName().compareTo(list2.getLabelName()))
		.collect(Collectors.toList());
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("label.getAllLabels"), sortedList);
	}

	// Sort all Label
	@Override
	public Response sortAllLabel() {
		List<LabelEntity> sortedList = labelRepository.findAll().stream()
		.sorted((list1, list2) -> list1.getLabelName().compareTo(list2.getLabelName()))
		.collect(Collectors.toList());
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("label.getAllLabels"), sortedList);
	}
}
