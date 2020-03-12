package com.bridgelabz.fundoonotesapi.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotesapi.dto.CollaboratorDto;
import com.bridgelabz.fundoonotesapi.exception.CollaboratorNotExist;
import com.bridgelabz.fundoonotesapi.exception.LoginException;
import com.bridgelabz.fundoonotesapi.exception.NoteNotFoundException;
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
			throw new LoginException(message.User_Not_Exist);
		// check if note exist or not
		NoteEntity noteEntity = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteNotFoundException(message.Note_Not_Exist));
		// check if it belong to user note
		if (noteEntity.getUserEntity().getId() == user.getId()) {
			// this check if reciver Email exist in user database
			for (UserEntity userEntity : userRepository.findAll()) {
				if (userEntity.getEmail().equals(collaboratorDto.getReciver())) {
					// this throw exception when reciver email is already present or collaborate
					for (CollaboratorEntity collaborator : noteEntity.getCollaboratorList()) {
						if (collaborator.getMailReciver().equals(collaboratorDto.getReciver()))
							throw new ReciverEmailofCollaborator(message.Reciver_Email_Already_Exist);
					}
					// save data into collaborator table
					CollaboratorEntity collaboratorData = mapper.map(collaboratorDto, CollaboratorEntity.class);
					collaboratorData.setMailReciver(collaboratorDto.getReciver());
					collaboratorData.setMailSender(user.getEmail());
					collaboratorData.setNoteEntity(noteEntity);
					collaboRepository.save(collaboratorData);
					return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("collaborator.add"), message.Collaborator);
				}
			}
			throw new ReciverEmailofCollaborator(message.Email_Not_Exist);
		}
		throw new NoteNotFoundException(message.Note_Not_Exist_User);
	}

	// Get All Collaborator
	@Override
	public Response getCollaborator(String token, int noteId) {
		String email = jwtToken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		// check if note exist or not
		NoteEntity noteEntity = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteNotFoundException(message.Note_Not_Exist));
		// check if it belong to user note
		if (noteEntity.getUserEntity().getId() == user.getId()) {
			if (noteEntity.getCollaboratorList() == null)
				throw new CollaboratorNotExist(message.Collaborator_Not_Exist);
			else
				return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("collaborator.display"), noteEntity.getCollaboratorList());
		}
		throw new NoteNotFoundException(message.Note_Not_Exist_User);
	}

	// Delete Collaborator
	@Override
	public Response deleteCollaborator(String token, int noteId, int collaboratorId) {
		String email = jwtToken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		// check if note exist or not
		NoteEntity noteEntity = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteNotFoundException(message.Note_Not_Exist));
		// check if collaborator id exist or not
		CollaboratorEntity collaboratorEntity = collaboRepository.findById(collaboratorId)
				.orElseThrow(() -> new CollaboratorNotExist(message.Collaborator_Not_Exist));
		// check if note belong to user note
		if (noteEntity.getUserEntity().getId() != user.getId())
			throw new NoteNotFoundException(message.Note_Not_Exist);
		// check if collaborator is exist in note else throw exception
		if (collaboratorEntity.getNoteEntity().getId() == noteId) {
			collaboRepository.deleteById(collaboratorId);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
			environment.getProperty("collaborator.delete"), message.Collaborator);
		} else
			throw new CollaboratorNotExist(message.Collaborator_Not_Exist);
	}
}
