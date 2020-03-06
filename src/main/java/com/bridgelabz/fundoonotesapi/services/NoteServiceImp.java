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
import com.bridgelabz.fundoonotesapi.exception.LabelAlreadyExist;
import com.bridgelabz.fundoonotesapi.exception.LabelNotExistException;
import com.bridgelabz.fundoonotesapi.exception.LoginException;
import com.bridgelabz.fundoonotesapi.exception.NoteNotExistInTrash;
import com.bridgelabz.fundoonotesapi.exception.NoteNotFoundException;
import com.bridgelabz.fundoonotesapi.message.MessageInfo;
import com.bridgelabz.fundoonotesapi.model.LabelEntity;
import com.bridgelabz.fundoonotesapi.model.NoteEntity;
import com.bridgelabz.fundoonotesapi.model.UserEntity;
import com.bridgelabz.fundoonotesapi.repository.LabelRepository;
import com.bridgelabz.fundoonotesapi.repository.NoteRepository;
import com.bridgelabz.fundoonotesapi.repository.UserRepository;
import com.bridgelabz.fundoonotesapi.response.Response;
import com.bridgelabz.fundoonotesapi.utility.JwtToken;
import com.sun.istack.logging.Logger;

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

	@Autowired
	LabelRepository labelRepository;
	
	private static final Logger LOGGER = Logger.getLogger(NoteServiceImp.class);

	// Create New Note
	@Override
	public Response createNote(String token, NoteDto noteDto) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check whether user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		NoteEntity noteData = mapper.map(noteDto, NoteEntity.class);
		noteData.setUserEntity(user);
		noteRepository.save(noteData);
		LOGGER.info("Note successfully created into Note table");
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("note.create"), message.Note_Create);
	}

	// add Label in Note
	@Override
	public Response addLabelInNote(String token, int noteId, int labelId) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check whether user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		NoteEntity noteData = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteNotFoundException(message.Note_Not_Exist));
		// check whether note is present or not
		if ((noteData == null) || (noteData.getUserEntity().getId() != user.getId()))
			throw new NoteNotFoundException(message.Note_Not_Exist);
		// check if label id is one from user labels if not the throw exception
		for (LabelEntity labelEntity : user.getLabelList()) {
			if (labelEntity.getId() == labelId) {
				List<LabelEntity> listOfLabel = noteData.getLabelList();
				List<NoteEntity> listOfNote = labelEntity.getNoteList();
				// this check if label is already add in note then only it will throw exception
				for (LabelEntity label : listOfLabel) {
					if (label.getId() == labelId) {
						throw new LabelAlreadyExist(message.Label_Already_Exist);
					}
				}
				// this set label list for note entity
				listOfLabel.add(labelEntity);
				noteData.setLabelList(listOfLabel);
				listOfNote.add(noteData);
				// this set note list for label entity
				labelEntity.setNoteList(listOfNote);
				noteRepository.save(noteData);
				labelRepository.save(labelEntity);
				LOGGER.info("Label is Successfully added into the Note");
				return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
						environment.getProperty("label.add"), message.Label_Create);
			}
		}
		throw new LabelNotExistException(message.Label_Not_Exist);
	}

	// Get all Notes
	@Override
	public Response getAllNotes(String token) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check whether user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		// check whether user has note or not
		if (user.getNoteList() == null)
			throw new NoteNotFoundException(message.Note_Not_Exist);
		List<NoteEntity> allNotes = user.getNoteList().stream().filter(noteData -> !noteData.isTrash())
				.collect(Collectors.toList());
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("note.getallnotes"), allNotes);
	}

	// get all Note by Label Id
	@Override
	public Response getNoteByLabelId(String token, int labelId) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check whether user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		// check whether user has note or not
		if (user.getNoteList() == null)
			throw new NoteNotFoundException(message.Note_Not_Exist);
		LabelEntity labelEntity = labelRepository.findById(labelId)
				.orElseThrow(() -> new LabelNotExistException(message.Label_Not_Exist));
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("note.getallnotes"), labelEntity.getNoteList());
	}

	// Update Note
	@Override
	public Response updateNote(String token, int noteId, NoteDto noteDto) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check whether user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		// check whether note is present or not
		NoteEntity noteData = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteNotFoundException(message.Note_Not_Exist));
		// if present then check if note belong to user
		if (noteData.getUserEntity().getId() != user.getId())
			throw new NoteNotFoundException(message.Note_Not_Exist_User);
		noteData.setDescription(noteDto.getDescription());
		noteData.setTitle(noteDto.getTitle());
		noteRepository.save(noteData);
		LOGGER.info("Note is successfully update and saved in tabel");
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("note.update"), message.Note_Update);
	}

	// Delete Note
	@Override
	public Response deleteNote(String token, int noteId) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check whether user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		// check whether note is present or not
		NoteEntity noteData = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteNotFoundException(message.Note_Not_Exist));
		// if present then check if note belong to user
		if (noteData.getUserEntity().getId() != user.getId())
			throw new NoteNotFoundException(message.Note_Not_Exist_User);
		// if isTrash is true then only it will delete note permanently else throw exception
		if (noteData.isTrash() == true) {
			noteRepository.deleteById(noteId);
			LOGGER.info("Note is successfully deleted from Note table");
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("note.delete"), message.Note_Delete);
		}
		throw new NoteNotExistInTrash(message.Note_Not_Exist_In_Trash);
	}

	// Pin or UnPin Note
	@Override
	public Response isPin(String token, int noteId) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check whether user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		// check whether note is present or not
		NoteEntity noteData = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteNotFoundException(message.Note_Not_Exist));
		// if present then check if note belong to user
		if (noteData.getUserEntity().getId() != user.getId())
			throw new NoteNotFoundException(message.Note_Not_Exist);
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
		// check whether user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		// check whether note is present or not
		NoteEntity noteData = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteNotFoundException(message.Note_Not_Exist));
		// if present then check if note belong to user
		if (noteData.getUserEntity().getId() != user.getId())
			throw new NoteNotFoundException(message.Note_Not_Exist_User);
		// isArchive is false then change it to true else false
		if (noteData.isArchive() == false) {
			noteData.setArchive(true);
			noteRepository.save(noteData);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("note.isarchive"), message.Note_Archive);
		} else {
			noteData.setArchive(false);
			noteRepository.save(noteData);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("note.isunarchive"), message.Note_UnArchive);
		}
	}

	// Trash or UnTrash
	@Override
	public Response isTrash(String token, int noteId) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check whether user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		// check whether note is present or not
		NoteEntity noteData = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteNotFoundException(message.Note_Not_Exist));
		// if present then check if note belong to user
		if (noteData.getUserEntity().getId() != user.getId())
			throw new NoteNotFoundException(message.Note_Not_Exist_User);
		// isTrash is false then change it to true else false
		if (noteData.isTrash() == false) {
			noteData.setTrash(true);
			noteRepository.save(noteData);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("note.istrash"), message.Note_Trash);
		} else {
			noteData.setTrash(false);
			noteRepository.save(noteData);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("note.isuntrash"), message.Note_UnTrash);
		}
	}

	/************************* Sorting Logics ******************************/

	// Sort User Notes by Title
	@Override
	public Response sortByNoteTitle(String token) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check whether user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		// check whether note is present or not
		if (user.getNoteList() == null)
			throw new NoteNotFoundException(message.Note_Not_Exist);
		// stream feature is used to sort the list
		List<NoteEntity> sortedList = user.getNoteList().stream()
				.sorted((list1, list2) -> list1.getTitle().compareTo(list2.getTitle())).collect(Collectors.toList());
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("note.getallnotes"), sortedList);
	}

	// Sort User Notes by Note Description
	@Override
	public Response sortByNoteDescription(String token) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check whether user is present or not
		if (user == null)
			throw new LoginException(message.User_Not_Exist);
		// check whether note is present or not
		if (user.getNoteList() == null)
			throw new NoteNotFoundException(message.Note_Not_Exist);
		// stream feature is used to sort the list
		List<NoteEntity> sortedList = user.getNoteList().stream()
				.sorted((list1, list2) -> list1.getDescription().compareTo(list2.getDescription()))
				.collect(Collectors.toList());
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("note.getallnotes"), sortedList);
	}
}
