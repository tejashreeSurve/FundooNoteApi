package com.bridgelabz.fundoonotesapi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotesapi.dto.CollaboratorDto;
import com.bridgelabz.fundoonotesapi.exception.ReciverEmailofCollaborator;
import com.bridgelabz.fundoonotesapi.message.MessageInfo;
import com.bridgelabz.fundoonotesapi.model.CollaboratorEntity;
import com.bridgelabz.fundoonotesapi.model.NoteEntity;
import com.bridgelabz.fundoonotesapi.model.UserEntity;
import com.bridgelabz.fundoonotesapi.repository.CollaboratorRepository;
import com.bridgelabz.fundoonotesapi.repository.NoteRepository;
import com.bridgelabz.fundoonotesapi.repository.UserRepository;
import com.bridgelabz.fundoonotesapi.response.Response;
import com.bridgelabz.fundoonotesapi.utility.JwtToken;

/**
 * @author Tejashree Surve
 * @Purpose : This is Service class for Collaborator which contain logic for all
 *          Api's.
 */
@Component
@Service
@PropertySource("message.properties")
public class CollaboratorServiceImp implements ICollaboratorService {

	@Autowired
	JwtToken jwtToken;

	@Autowired
	UserRepository userRepository;

	@Autowired
	Environment environment;

	@Autowired
	ModelMapper mapper;

	@Autowired
	NoteRepository noteRepository;

	@Autowired
	MessageInfo message;

	@Autowired
	CollaboratorRepository collaboRepository;

	// Add Collaborator
	@Override
	public Response addCollaborator(String token, int noteId, CollaboratorDto collaboratorDto) {
		String email = jwtToken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		// check if note is present or not
		if (user.getNoteEntity().contains((noteRepository.findById(noteId)))) {
			for (UserEntity userEntity : userRepository.findAll()) {
				for(CollaboratorEntity collaborator : collaboRepository.findAll()) {
				if(userEntity.getEmail().equals(collaboratorDto.getReciver()) && !collaborator.getMailReciver().equals(collaboratorDto.getReciver())) {
				// save data into collaborator tabel
				CollaboratorEntity collaboratorData = mapper.map(collaboratorDto, CollaboratorEntity.class);
				collaboratorData.setMailReciver(collaboratorDto.getReciver());
				collaboratorData.setMailSender(user.getEmail());
				collaboratorData.setNoteEntity(noteRepository.findById(noteId));
				collaboRepository.save(collaboratorData);
				return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("collaborator.add"), message.Collaborator);
				}
				}
			}
			throw new ReciverEmailofCollaborator(message.Reciver_Email_Collaborator);
		}
		return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
				environment.getProperty("note.notexist"), message.Note_Not_Exist);
	}

	// Get All Collaborator
	@Override
	public Response getCollaborator(String token, int noteId) {
		String email = jwtToken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		// check if note is present or not
		if(user.getNoteEntity().contains((noteRepository.findById(noteId)))) {
			if(noteRepository.findById(noteId).getCollaboratorList() == null)
				return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
				environment.getProperty("collaborator.isnotpresent"), message.Collaborator);
			else {
					return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("collaborator.display"), noteRepository.findById(noteId).getCollaboratorList());
				}
			}
				return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
				environment.getProperty("note.notexist"), message.Note_Not_Exist);
	}

	// Delete Collaborator
	@Override
	public Response deleteCollaborator(String token, int noteId, int collaboratorId) {
		String email = jwtToken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		// check if note is present or not
		if(user.getNoteEntity().contains((noteRepository.findById(noteId)))) {
			if(noteRepository.findById(noteId).getCollaboratorList() == null)
				return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
				environment.getProperty("collaborator.isnotpresent"), message.Collaborator);
			else {
				collaboRepository.deleteById(collaboratorId);
			}
		}
		return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
		environment.getProperty("note.notexist"), message.Note_Not_Exist);
	}
}
