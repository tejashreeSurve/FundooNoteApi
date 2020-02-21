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
	JwtToken jwttoken;

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
	public Response addCollaborator(String token, int noteid, CollaboratorDto collaboratorDto) {
		String email = jwttoken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		// this get all note of that user
		List<NoteEntity> allNotedata = noteRepository.findAll();
		List<NoteEntity> listOfNotes = allNotedata.stream()
				.filter(userdata -> userdata.getUserEntity().getId() == user.getId()).collect(Collectors.toList());
		listOfNotes.stream().forEach(list -> System.out.println(list.getId()));
		// check if note id == to user present note id
		for (int i = 0; i < listOfNotes.size(); i++) {
			if (listOfNotes.get(i).getId() == noteid) {
				// Setting collaborator fields
				NoteEntity noteData = noteRepository.findById(noteid);
				CollaboratorEntity collaboratorData = mapper.map(collaboratorDto, CollaboratorEntity.class);
				collaboratorData.setMailreciver(collaboratorDto.getReciver());
				collaboratorData.setMailsender(user.getEmail());
				collaboratorData.setNoteEntity(noteData);
				collaboRepository.save(collaboratorData);
				return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
						environment.getProperty("collaborator.add"), message.Collaborator);
			}
		}
		return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
				environment.getProperty("note.notexist"), message.Note_Not_Exist);
	}

	// Get All Collaborator
	@Override
	public Response getCollaborator(String token, int noteid) {
		String email = jwttoken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		// this get all note of that user
		List<NoteEntity> allNotedata = noteRepository.findAll();
		List<NoteEntity> listOfNotes = allNotedata.stream()
				.filter(userdata -> userdata.getUserEntity().getId() == user.getId()).collect(Collectors.toList());
		listOfNotes.stream().forEach(list -> System.out.println(list.getId()));
		// check if note id == to user present note id
		for (int i = 0; i < listOfNotes.size(); i++) {
			if (listOfNotes.get(i).getId() == noteid) {
				// Setting collaborator fields
				NoteEntity noteData = noteRepository.findById(noteid);
				if (noteData.getCollaboratorList() != null) {
					return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
							environment.getProperty("collaborator.display"), noteData.getCollaboratorList());
				}
				return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
						environment.getProperty("collaborator.isnotpresent"), message.Collaborator);
			}
		}
		return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
				environment.getProperty("note.notexist"), message.Note_Not_Exist);
	}

	// Delete Collaborator
	@Override
	public Response deleteCollaborator(String token, int noteid, CollaboratorDto collaboratorDto) {
		String email = jwttoken.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		// this get all note of that user
		List<NoteEntity> allNotedata = noteRepository.findAll();
		List<NoteEntity> listOfNotes = allNotedata.stream()
				.filter(userdata -> userdata.getUserEntity().getId() == user.getId()).collect(Collectors.toList());
		listOfNotes.stream().forEach(list -> System.out.println(list.getId()));
		// check if note id == to user present note id
		NoteEntity noteData = noteRepository.findById(noteid);
		for (int i = 0; i < listOfNotes.size(); i++) {
			if (listOfNotes.get(i).getId() == noteid) {
				for (int j = 0; j < noteData.getCollaboratorList().size(); j++) {
					if (noteData.getCollaboratorList().get(j).getMailreciver().equals(collaboratorDto.getReciver())) {
						collaboRepository.deleteById(noteData.getCollaboratorList().get(j).getId());
						return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
								environment.getProperty("collaborator.delete"), message.Collaborator);
					}
				}
				return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
						environment.getProperty("collaborator.isnotpresent"), message.Collaborator);
			}
		}
		return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
				environment.getProperty("note.notexist"), message.Note_Not_Exist);
	}
}
