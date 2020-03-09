package com.bridgelabz.fundoonotesapi.services;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.bridgelabz.fundoonotesapi.dto.NoteDto;
import com.bridgelabz.fundoonotesapi.exception.LoginException;
import com.bridgelabz.fundoonotesapi.message.MessageInfo;
import com.bridgelabz.fundoonotesapi.model.NoteEntity;
import com.bridgelabz.fundoonotesapi.model.UserEntity;
import com.bridgelabz.fundoonotesapi.repository.LabelRepository;
import com.bridgelabz.fundoonotesapi.repository.NoteRepository;
import com.bridgelabz.fundoonotesapi.repository.UserRepository;
import com.bridgelabz.fundoonotesapi.response.Response;
import com.bridgelabz.fundoonotesapi.utility.JwtToken;
import com.sun.istack.logging.Logger;

public class ElasticSearchNoteImp implements IElasticSearchNoteService{
	@Autowired
	JwtToken jwtOperation;

	@Autowired
	UserRepository userRepository;

	@Autowired
	Environment environment;

	@Autowired
	NoteRepository noteRepository;

	@Autowired
	ModelMapper mapper;

	@Autowired
	MessageInfo message;

	@Autowired
	UserEntity UserEntity;

	@Autowired
	LabelRepository labelRepository;
	
	@Autowired
	ElasticSearchNoteService elasticSearch;
	
	private static final Logger LOGGER = Logger.getLogger(NoteServiceImp.class);
	@Override
	public Response createNote(String token, NoteDto noteDto) throws IOException {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check whether user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		NoteEntity noteData = mapper.map(noteDto, NoteEntity.class);
		noteData.setUserEntity(user);
		elasticSearch.createNoteInElastic(noteData);
		LOGGER.info("Note successfully created into Note table");
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("note.create"), message.Note_Create);
		
	}

	@Override
	public Response getAllNotes() throws IOException {
//		String email = jwtOperation.getToken(token);
//		UserEntity user = userRepository.findByEmail(email);
//		// check whether user is present or not
//		if (user == null)
//			throw new LoginException(message.User_Not_Exist);
//		// check whether user has note or not
//		if (user.getNoteList() == null)
//			throw new NoteNotFoundException(message.Note_Not_Exist);
//		List<NoteEntity> allNotes = elasticSearch.getAllNote().stream().filter(noteData -> !noteData.isTrash())
//				.collect(Collectors.toList());
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("note.getallnotes"), elasticSearch.getAllNote());
	}
	
	

}
