//package com.bridgelabz.fundoonotesapi.services;
//
//import java.io.IOException;
//import java.util.List;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Service;
//
//import com.bridgelabz.fundoonotesapi.dto.NoteDto;
//import com.bridgelabz.fundoonotesapi.exception.LoginException;
//import com.bridgelabz.fundoonotesapi.message.MessageInfo;
//import com.bridgelabz.fundoonotesapi.model.NoteEntity;
//import com.bridgelabz.fundoonotesapi.model.UserEntity;
//import com.bridgelabz.fundoonotesapi.repository.LabelRepository;
//import com.bridgelabz.fundoonotesapi.repository.NoteRepository;
//import com.bridgelabz.fundoonotesapi.repository.UserRepository;
//import com.bridgelabz.fundoonotesapi.response.Response;
//import com.bridgelabz.fundoonotesapi.utility.JwtToken;
//import com.sun.istack.logging.Logger;
//
//@Service
//public class ElasticSearchNoteImp {
//	@Autowired
//	JwtToken jwtOperation;
//
//	@Autowired
//	UserRepository userRepository;
//
//	@Autowired
//	Environment environment;
//
//	@Autowired
//	NoteRepository noteRepository;
//
//	@Autowired
//	ModelMapper mapper;
//
//	@Autowired
//	MessageInfo message;
//
//	@Autowired
//	UserEntity UserEntity;
//
//	@Autowired
//	LabelRepository labelRepository;
//
//	@Autowired
//	ElasticSearchNoteService elasticSearch;
//
//	private static final Logger LOGGER = Logger.getLogger(NoteServiceImp.class);
//
//	public String createNote(String token, NoteDto noteDto) throws IOException {
//		String email = jwtOperation.getToken(token);
//		UserEntity user = userRepository.findByEmail(email);
//		// check whether user is present or not
//		if (user == null)
//			throw new LoginException(message.User_Not_Exist);
//		NoteEntity noteData = mapper.map(noteDto, NoteEntity.class);
//		noteData.setUserEntity(user);
//		System.out.println("i m here");
//		elasticSearch.createNoteInElastic(noteData);
//		LOGGER.info("Note successfully created into Note table");
//		return "note is Successfully created !!!";
//
//	}
//
//	public List<NoteEntity> getAllNote() throws IOException {
//		return elasticSearch.getAllNote();
//	}
//
//	public NoteEntity getNote(String id) throws IOException {
//		return elasticSearch.findByIdByElasticSearch(id);
//	}
//
//	public String deleteNote(String id) {
//		return deleteNote(id);
//	}
//
//}
