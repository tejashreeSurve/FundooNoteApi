package com.bridgelabz.fundoonotesapi.services;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotesapi.dto.NoteDto;
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
	public Response createNote(String token,NoteDto notedto) {
		String email = jwtOperation.getToken(token);
		
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		NoteEntity noteData = mapper.map(notedto, NoteEntity.class);
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
		List<NoteEntity> allNotedata = noteRepository.findAll();
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		if (allNotedata == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.notexist"), message.Note_Not_Exist);
		// this stream filter those notes which contains user id is equal to given token
		List<NoteEntity> listOfNotes = allNotedata.stream()
				.filter(userdata -> userdata.getUserEntity().getId() == user.getId()).collect(Collectors.toList());
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("note.getallnotes"), listOfNotes);
	}

	// Update Note
	@Override
	public Response updateNote(String token, int id, NoteDto notedto) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		NoteEntity noteData = noteRepository.findById(id);
		if (noteData == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.notexist"), message.Note_Not_Exist);
		else {
			noteData.setDescription(notedto.getDescription());
			noteData.setTitle(notedto.getTitle());
			noteRepository.save(noteData);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("note.update"), message.Note_Update);
		}
	}

	// Delete Note
	@Override
	public Response deleteNote(String token, int id) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		if (noteRepository.findById(id) == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.notexist"), message.Note_Not_Exist);
		else {
			noteRepository.deleteById(id);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("note.delete"), message.Note_Delete);
		}
	}

	// Pin or UnPin Note
	@Override
	public Response pinOrUnpin(String token, int id) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		NoteEntity notedata = noteRepository.findById(id);
		if (notedata == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.notexist"), message.Note_Not_Exist);

		if (notedata.isPinOrUnPin() == false) {
			notedata.setPinOrUnPin(true);
			noteRepository.save(notedata);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("note.ispin"), message.Note_Pin);
		} else {
			notedata.setPinOrUnPin(false);
			noteRepository.save(notedata);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("note.isunpin"), message.Note_UnPin);
		}
	}

	// Archive or UnArchive
	@Override
	public Response archiveOrUnarchive(String token, int id) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		NoteEntity notedata = noteRepository.findById(id);
		if (notedata == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.notexist"), message.Note_Not_Exist);

		if (notedata.isArchiveOrUnArchive() == false) {
			notedata.setArchiveOrUnArchive(true);
			noteRepository.save(notedata);
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.isarchive"), message.Note_Archive);
		} else {
			notedata.setArchiveOrUnArchive(false);
			noteRepository.save(notedata);
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.isunarchive"), message.Note_UnArchive);
		}
	}

	// Trash or UnTrash
	@Override
	public Response trashOrUntrash(String token, int id) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		NoteEntity notedata = noteRepository.findById(id);
		if (notedata == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.notexist"), message.Note_Not_Exist);

		if (notedata.isTrashOrUnTrash() == false) {
			notedata.setTrashOrUnTrash(true);
			noteRepository.save(notedata);
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.istrash"), message.Note_Trash);
		} else {
			notedata.setTrashOrUnTrash(false);
			noteRepository.save(notedata);
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.isuntrash"), message.Note_UnTrash);
		}
	}

	@Override
	public Response sortAllNoteBytitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response sortByNotetitle(String token) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		List<NoteEntity> allNotedata = noteRepository.findAll();
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		if (allNotedata == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.notexist"), message.Note_Not_Exist);
		// this stream filter those notes which contains user id is equal to given token
		List<NoteEntity> listOfNotes = allNotedata.stream()
				.filter(userdata -> userdata.getUserEntity().getId() == user.getId()).collect(Collectors.toList());
		List<NoteEntity> sortedList = listOfNotes.stream().sorted(listOfNotes.forEach()).collect(Collectors.toList());
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("note.getallnotes"), sortedList);
	}

	@Override
	public Response sortByNoteDate(String token) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
