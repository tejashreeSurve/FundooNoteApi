package com.bridgelabz.fundoonotesapi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotesapi.dto.NoteDto;
import com.bridgelabz.fundoonotesapi.exception.ReminderNotPresentException;
import com.bridgelabz.fundoonotesapi.message.MessageInfo;
import com.bridgelabz.fundoonotesapi.model.NoteEntity;
import com.bridgelabz.fundoonotesapi.model.UserEntity;
import com.bridgelabz.fundoonotesapi.repository.NoteRepository;
import com.bridgelabz.fundoonotesapi.repository.UserRepository;
import com.bridgelabz.fundoonotesapi.response.Response;
import com.bridgelabz.fundoonotesapi.utility.JwtToken;

/**
 * @author Tejashree Surve
 * @Purpose : This is Service class of Note which contain logic for all Api's.
 */
@Component
@Service
@PropertySource("message.properties")
public class NoteServiceImp implements INoteService {

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

	// Create New Note
	@Override
	public Response createNote(String token, NoteDto noteDto) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check whether user is present or not
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		
		NoteEntity noteData = mapper.map(noteDto, NoteEntity.class);
		noteData.setUserEntity(user);
		noteRepository.save(noteData);
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("note.create"), message.Note_Create);
	}

	// Get all Notes
	@Override
	public Response getAllNotes(String token) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		// check whether user has note or not
		if (user.getNoteEntity() == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.notexist"), message.Note_Not_Exist);
		
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("note.getallnotes"), user.getNoteEntity());
	}

	// Update Note
	@Override
	public Response updateNote(String token, int noteId, NoteDto noteDto) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		// check whether note is present or not 
		NoteEntity noteData = noteRepository.findById(noteId);
		if (noteRepository.findById(noteId) == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.notexist"), message.Note_Not_Exist);
		
		noteData.setDescription(noteDto.getDescription());
		noteData.setTitle(noteDto.getTitle());
		noteRepository.save(noteData);
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("note.update"), message.Note_Update);
	}

	// Delete Note
	@Override
	public Response deleteNote(String token, int noteId) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		// check whether note is present or not 
		if (noteRepository.findById(noteId) == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.notexist"), message.Note_Not_Exist);
		
		noteRepository.deleteById(noteId);
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("note.delete"), message.Note_Delete);
	}

	// Pin or UnPin Note
	@Override
	public Response isPin(String token, int noteId) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		
		NoteEntity noteData = noteRepository.findById(noteId);
		if (noteData == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.notexist"), message.Note_Not_Exist);
		// isPin is false then change it to true else false
		if (noteData.isPin() == false) {
			noteData.setPin(true);
			noteRepository.save(noteData);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
			environment.getProperty("note.ispin"), message.Note_Pin);
		} else {
			noteData.setPin(false);
			noteRepository.save(noteData);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
			environment.getProperty("note.isunpin"), message.Note_UnPin);
		}
	}

	// Archive or UnArchive
	@Override
	public Response isArchive(String token, int noteId) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		
		NoteEntity noteData = noteRepository.findById(noteId);
		if (noteData == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.notexist"), message.Note_Not_Exist);
		// isArchive is false then change it to true else false
		if (noteData.isArchive() == false) {
			noteData.setArchive(true);
			noteRepository.save(noteData);
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.isarchive"), message.Note_Archive);
		} else {
			noteData.setArchive(false);
			noteRepository.save(noteData);
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.isunarchive"), message.Note_UnArchive);
		}
	}

	// Trash or UnTrash
	@Override
	public Response isTrash(String token, int noteId) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		
		NoteEntity noteData = noteRepository.findById(noteId);
		if (noteData == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.notexist"), message.Note_Not_Exist);
		// isTrash is false then change it to true else false
		if (noteData.isTrash() == false) {
			noteData.setTrash(true);
			noteRepository.save(noteData);
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.istrash"), message.Note_Trash);
		} else {
			noteData.setTrash(false);
			noteRepository.save(noteData);
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.isuntrash"), message.Note_UnTrash);
		}
	}

	/**************************************** Sorting Logic *****************************************/

	// Sort all Notes by Title
	@Override
	public Response sortAllNoteByTitle() {
		List<NoteEntity> sortedList = noteRepository.findAll().stream()
				.sorted((list1, list2) -> list1.getTitle().compareTo(list2.getTitle())).collect(Collectors.toList());
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("note.getallnotes"), sortedList);
	}

	// Sort User Notes by Title
	@Override
	public Response sortByNoteTitle(String token) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("status.email.notexist"), message.User_Not_Exist);

		if (user.getNoteEntity() == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.notexist"), message.Note_Not_Exist);
		// stream feature is used to sort the list
		List<NoteEntity> sortedList = user.getNoteEntity().stream()
		.sorted((list1, list2) -> list1.getTitle().compareTo(list2.getTitle())).collect(Collectors.toList());
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("note.getallnotes"), sortedList);
	}

	// Sort User Notes by Reminder Date
	@Override
	public Response sortByNoteDate(String token) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		
		
		 if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("status.email.notexist"), message.User_Not_Exist);
	
		if (user.getNoteEntity() == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.notexist"), message.Note_Not_Exist);
//		
//		for (NoteEntity noteEntity : user.getNoteEntity()) {
//			if(noteEntity.getReminderEntity() == null )
//				throw new ReminderNotPresentException(message.Reminder_isNotPresent);
//		}
		
		// stream feature is used to sort the list
				List<NoteEntity> sortedList = user.getNoteEntity().stream()
						.sorted((list1, list2) -> list1.getReminderEntity().getTime().compareTo(list2.getReminderEntity().getTime()))
						.collect(Collectors.toList());
				return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
						environment.getProperty("note.getallnotes"), sortedList);
	}

	// Sort User Notes by Note Description
	@Override
	public Response sortByNoteDescription(String token) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("status.email.notexist"), message.User_Not_Exist);

		if (user.getNoteEntity() == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.notexist"), message.Note_Not_Exist);
		// stream feature is used to sort the list
		List<NoteEntity> sortedList = user.getNoteEntity().stream()
				.sorted((list1, list2) -> list1.getDescription().compareTo(list2.getDescription()))
				.collect(Collectors.toList());
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("note.getallnotes"), sortedList);
	}
}
