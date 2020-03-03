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

import com.bridgelabz.fundoonotesapi.dto.ReminderDto;
import com.bridgelabz.fundoonotesapi.message.MessageInfo;
import com.bridgelabz.fundoonotesapi.model.NoteEntity;
import com.bridgelabz.fundoonotesapi.model.ReminderEntity;
import com.bridgelabz.fundoonotesapi.model.UserEntity;
import com.bridgelabz.fundoonotesapi.repository.NoteRepository;
import com.bridgelabz.fundoonotesapi.repository.ReminderRepository;
import com.bridgelabz.fundoonotesapi.repository.UserRepository;
import com.bridgelabz.fundoonotesapi.response.Response;
import com.bridgelabz.fundoonotesapi.utility.JwtToken;

/**
 * @author Tejashree Surve
 * @Purpose : This is Service class for Reminder which contain logic for all Api's.
 */
@Component
@Service
@PropertySource("message.properties")
public class ReminderServiceImp implements IReminderService {

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
	ReminderRepository reminderRepository;

	// add reminder to note
	@Override
	public Response addReminder(String token, int noteId, ReminderDto reminderDto) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not from token
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		NoteEntity noteEntity = noteRepository.findById(noteId);
		if (!user.getNoteEntity().contains(noteEntity))
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.notexist"), message.Note_Not_Exist);
		
		if(noteEntity.getReminderEntity() == null) {
			ReminderEntity reminderData = mapper.map(reminderDto, ReminderEntity.class);
			reminderData.setNoteEntity(noteEntity);
			reminderRepository.save(reminderData);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
			environment.getProperty("reminder.add"), message.Reminder_isSet);
		}
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
			environment.getProperty("reminder.ispresent"), message.Reminder_isPresent);
	}

	// show all note which contain reminder
	@Override
	public Response getReminder(String token) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		
		// check if user is present or not
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		// check id note is present or not
		List<NoteEntity> userNoteList = user.getNoteEntity();
		if (userNoteList == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.notexist"), message.Note_Not_Exist);
		List<NoteEntity> notesReminder = new ArrayList<NoteEntity>();
		for (NoteEntity noteEntity : userNoteList) {
			if(noteEntity.getReminderEntity() != null) {
				notesReminder.add(noteEntity);
			}
		}
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("reminder.show"), notesReminder);
	}

	// update reminder using note id
	@Override
	public Response updateReminder(String token, int noteId, ReminderDto reminderDto) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		
		NoteEntity noteEntity = noteRepository.findById(noteId);
		if (noteEntity == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.notexist"), message.Note_Not_Exist);
		
			if(noteEntity.getReminderEntity() != null ) {
				ReminderEntity reminderData = noteEntity.getReminderEntity();
				reminderData.setDate(reminderDto.getDate());
				reminderData.setTime(reminderDto.getTime());
				reminderRepository.save(reminderData);
				return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("reminder.update"), message.Reminder_isUpdate);
			}
		
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("reminder.isnotpresent"), message.Reminder_isNotPresent);
	}

	// delete reminder of note
	@Override
	public Response deleteReminder(String token, int noteId) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);

		NoteEntity noteEntity = noteRepository.findById(noteId);
		if (noteEntity == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.notexist"), message.Note_Not_Exist);
		
		if(noteEntity.getReminderEntity() != null) {
			ReminderEntity reminderData = noteEntity.getReminderEntity();
			reminderRepository.deleteById(reminderData.getId());
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("reminder.delete"), message.Reminder_Delete);
		}
		
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
		environment.getProperty("reminder.isnotpresent"), message.Reminder_isNotPresent);
	}

	// set reminder repeat to daily option
	@Override
	public Response repeatDaily(String token, int noteId) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);

		NoteEntity noteEntity = noteRepository.findById(noteId);
		if (noteEntity == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.notexist"), message.Note_Not_Exist);
		
		if(noteEntity.getReminderEntity() != null) {
			if ((noteEntity.getReminderEntity().isRepeatMonthly() == false) && (noteEntity.getReminderEntity().isRepeatWeekly() == false)
					&& (noteEntity.getReminderEntity().isRepeatYearly() == false)) {
				noteEntity.getReminderEntity().setRepeatDaily(true);
				noteEntity.getReminderEntity().setDoNotRepeat(false);
				reminderRepository.save(noteEntity.getReminderEntity());
				return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
						environment.getProperty("reminder.set.daily"), message.Reminder_SetToRepeat);
			} 
		}
		return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
		environment.getProperty("reminder.notset"), message.Reminder_NotSet);
		
	}

	// set reminder repeat to weekly option
	@Override
	public Response repeatWeekly(String token, int noteId) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);

		NoteEntity noteEntity = noteRepository.findById(noteId);
		if (noteEntity == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.notexist"), message.Note_Not_Exist);
		
		if(noteEntity.getReminderEntity() != null) {
			if ((noteEntity.getReminderEntity().isRepeatMonthly() == false) && (noteEntity.getReminderEntity().isRepeatDaily() == false)
					&& (noteEntity.getReminderEntity().isRepeatYearly() == false)) {
				noteEntity.getReminderEntity().setRepeatWeekly(true);
				noteEntity.getReminderEntity().setDoNotRepeat(false);
				reminderRepository.save(noteEntity.getReminderEntity());
				return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
						environment.getProperty("reminder.set.weekly"), message.Reminder_SetToRepeat);
			} 
		}
		return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
		environment.getProperty("reminder.notset"), message.Reminder_NotSet);
		
	}

	// set reminder repeat to monthly option
	@Override
	public Response repeatMonthly(String token, int noteId) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);

		NoteEntity noteEntity = noteRepository.findById(noteId);
		if (noteEntity == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.notexist"), message.Note_Not_Exist);
		
		if(noteEntity.getReminderEntity() != null) {
			if ((noteEntity.getReminderEntity().isRepeatDaily() == false) && (noteEntity.getReminderEntity().isRepeatWeekly() == false)
					&& (noteEntity.getReminderEntity().isRepeatYearly() == false)) {
				noteEntity.getReminderEntity().setRepeatMonthly(true);
				noteEntity.getReminderEntity().setDoNotRepeat(false);
				reminderRepository.save(noteEntity.getReminderEntity());
				return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
						environment.getProperty("reminder.set.monthly"), message.Reminder_SetToRepeat);
			} 
		}
		return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
		environment.getProperty("reminder.notset"), message.Reminder_NotSet);
		
	}

	// set reminder repeat to yearly option
	@Override
	public Response repeatYearly(String token, int noteId) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);

		NoteEntity noteEntity = noteRepository.findById(noteId);
		if (noteEntity == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
			environment.getProperty("note.notexist"), message.Note_Not_Exist);
		
		if(noteEntity.getReminderEntity() != null) {
			if ((noteEntity.getReminderEntity().isRepeatMonthly() == false) && (noteEntity.getReminderEntity().isRepeatWeekly() == false)
					&& (noteEntity.getReminderEntity().isRepeatDaily() == false)) {
				noteEntity.getReminderEntity().setRepeatYearly(true);
				noteEntity.getReminderEntity().setDoNotRepeat(false);
				reminderRepository.save(noteEntity.getReminderEntity());
				return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
						environment.getProperty("reminder.set.yearly"), message.Reminder_SetToRepeat);
			} 
		}
		return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
		environment.getProperty("reminder.notset"), message.Reminder_NotSet);
	}
}
