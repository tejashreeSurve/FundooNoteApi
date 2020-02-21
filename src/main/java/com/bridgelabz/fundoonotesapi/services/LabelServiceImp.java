package com.bridgelabz.fundoonotesapi.services;

import java.util.ArrayList;
import java.util.List;
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
	JwtToken jwttoken;

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
	public Response addLabel(String token, int id, LabelDto labeldto) {
		String email = jwttoken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		NoteEntity notedata = noteRepository.findById(id);
		
		// check if note is present or not
		if (notedata == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.notexist"), message.Note_Not_Exist);
		else {
			// setting list of notes in LabelEntity to list
			LabelEntity labelEntity = mapper.map(labeldto, LabelEntity.class);
			List<NoteEntity> notelist = labelEntity.getNoteList();
			notelist.add(notedata);
			labelEntity.setNoteList(notelist);
			// setting the list of label in NoteEntity to list
			List<LabelEntity> labellist = notedata.getLabelList();
			labellist.add(labelEntity);
			notedata.setLabelList(labellist);

			labelRepository.save(labelEntity);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("label.create"), message.Label_Create);
		}
	}

	// Get all Label of User
	@Override
	public Response getAlllabel(String token) {
		String email = jwttoken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		List<NoteEntity> notelist = noteRepository.findAll();
		// this list contains all notes which is related to user 
		List<NoteEntity> listOfNotes = notelist.stream()
				.filter(userdata -> userdata.getUserEntity().getId() == user.getId()).collect(Collectors.toList());
		List<LabelEntity> labellist = new ArrayList<LabelEntity>();
		// this for loop get all label present in that notes list
		for(int i=0;i<listOfNotes.size();i++) {
			for(int j=0;j<listOfNotes.get(i).getLabelList().size();j++) {
			labellist.add(listOfNotes.get(i).getLabelList().get(j));
			}
		}
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("label.getAllLabels"), labellist);
	}

	// Update Label
	@Override
	public Response updateLabel(String token, int id, LabelDto labeldto) {
		String email = jwttoken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		LabelEntity labelData = labelRepository.findById(id);
		if(labelData == null )
			return new Response(Integer.parseInt(environment.getProperty("status.redirect.code")),
					environment.getProperty("label.notexist"), message.Label_Not_Exist);
		labelData.setLabelname(labeldto.getLabelname());
		labelRepository.save(labelData);
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("label.update"), message.Label_Update);
	}

	// Delete Label
	@Override
	public Response deleteLabel(String token, int id) {
		String email = jwttoken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		LabelEntity labelData = labelRepository.findById(id);
		if(labelData == null )
			return new Response(Integer.parseInt(environment.getProperty("status.redirect.code")),
					environment.getProperty("label.notexist"), message.Label_Not_Exist);
		labelRepository.deleteById(id);
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("label.delete"), message.Label_Delete);
	}

	// Get Notes where Label is present by Id
	@Override
	public Response getNoteByLabelId(int labelid) {
		LabelEntity labelData = labelRepository.findById(labelid);
		if(labelData == null )
			return new Response(Integer.parseInt(environment.getProperty("status.redirect.code")),
					environment.getProperty("label.notexist"), message.Label_Not_Exist);
		List<NoteEntity> notelist = noteRepository.findAll();
		List<LabelEntity> notes = new ArrayList<LabelEntity>();
		// this for loop will get all notes where label id == id
		for(int i=0;i<notelist.size();i++) {
			for(int j=0;j<notelist.get(i).getLabelList().size();j++) {
				if(notelist.get(i).getLabelList().get(j).getId() == labelid)
					notes.add(notelist.get(i).getLabelList().get(j));
			}
		}
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("note.getallnotes"), notes);
	}

	// Get all Notes where Label is present by Label Name
	@Override
	public Response getNoteByLabelName(LabelDto labeldto) {
		List<NoteEntity> notelist = noteRepository.findAll();
		List<LabelEntity> notes = new ArrayList<LabelEntity>();
		// this for loop will get all notes where label name is equal to name
		for(int i=0;i<notelist.size();i++) {
			for(int j=0;j<notelist.get(i).getLabelList().size();j++) {
				if(notelist.get(i).getLabelList().get(j).getLabelname() .equals(labeldto.getLabelname()))
					notes.add(notelist.get(i).getLabelList().get(j));
			}
		}
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("note.getallnotes"), notes);
	}
	
	// Change Label by Label ID's
	@Override
	public Response changeLabel(ChangeLabelDto changelabel) {
		LabelEntity labelOldData = labelRepository.findById(changelabel.getPreLabelId());
		LabelEntity labelChangeData = labelRepository.findById(changelabel.getChangeLabelId());
		if(labelOldData == null || labelChangeData == null) {
			return new  Response(Integer.parseInt(environment.getProperty("status.redirect.code")),
					environment.getProperty("label.notexist"), message.Label_Not_Exist);
		}
		labelChangeData.setLabelname(labelOldData.getLabelname());
		labelRepository.save(labelChangeData);
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("label.change"), message.Label_Change);
	}
	}
