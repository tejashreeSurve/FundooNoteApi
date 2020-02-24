package com.bridgelabz.fundoonotesapi.services;

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
	public Response addReminder(String token, int noteid, ReminderDto reminderDto) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not from token
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
				NoteEntity noteData = noteRepository.findById(noteid);
				// check if reminder is present or not in note
				if (noteData.getReminderEntity() == null) {
					ReminderEntity reminderData = mapper.map(reminderDto, ReminderEntity.class);
					reminderData.setNoteEntity(noteData);
					reminderRepository.save(reminderData);
					return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
							environment.getProperty("reminder.add"), message.Reminder_isSet);
				} else {
					return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
							environment.getProperty("reminder.ispresent"), message.Reminder_isPresent);
				}
			}
		}
		return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
				environment.getProperty("note.notexist"), message.Note_Not_Exist);
	}

	// show all note which contain reminder
	@Override
	public Response getReminder(String token) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		List<NoteEntity> allNotedata = noteRepository.findAll();
		// check if user is present or not
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		// check id note is present or not
		if (allNotedata == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.notexist"), message.Note_Not_Exist);
		// this stream filter those notes which contains user id is equal to given token
		List<NoteEntity> listOfNotes = allNotedata.stream()
				.filter(userdata -> userdata.getUserEntity().getId() == user.getId()).collect(Collectors.toList());
		List<NoteEntity> notesReminder = listOfNotes.stream().filter(reminder -> reminder.getReminderEntity() != null)
				.collect(Collectors.toList());
		return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("reminder.show"), notesReminder);
	}

	// update reminder using note id
	@Override
	public Response updateReminder(String token, int noteid, ReminderDto reminderDto) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		// this filter all note of user
		List<NoteEntity> allNotedata = noteRepository.findAll();
		List<NoteEntity> listOfNotes = allNotedata.stream()
				.filter(userdata -> userdata.getUserEntity().getId() == user.getId()).collect(Collectors.toList());
		listOfNotes.stream().forEach(list -> System.out.println(list.getId()));
		// check if note id is present in user note id
		for (int i = 0; i < listOfNotes.size(); i++) {
			if (listOfNotes.get(i).getId() == noteid) {
				NoteEntity noteData = noteRepository.findById(noteid);
				// check if reminder is present in note or not
				if (noteData.getReminderEntity() != null) {
					ReminderEntity reminderData = noteData.getReminderEntity();
					reminderData.setDate(reminderDto.getDate());
					reminderData.setTime(reminderDto.getTime());
					reminderRepository.save(reminderData);
					return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
							environment.getProperty("reminder.update"), message.Reminder_isUpdate);
				} else {
					return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
							environment.getProperty("reminder.isnotpresent"), message.Reminder_isNotPresent);
				}
			}
		}
		return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
				environment.getProperty("note.notexist"), message.Note_Not_Exist);
	}

	// delete reminder of note
	@Override
	public Response deleteReminder(String token, int noteid) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		// check if user is present or not
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		List<NoteEntity> allNotedata = noteRepository.findAll();
		List<NoteEntity> listOfNotes = allNotedata.stream()
				.filter(userdata -> userdata.getUserEntity().getId() == user.getId()).collect(Collectors.toList());
		// check if note is present or not
		if (listOfNotes == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.notexist"), message.Note_Not_Exist);
		// check if reminder is present or not
		NoteEntity noteData = noteRepository.findById(noteid);
		if (noteData.getReminderEntity() != null) {
			ReminderEntity reminderData = noteData.getReminderEntity();
			reminderRepository.deleteById(reminderData.getId());
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("reminder.delete"), message.Reminder_Delete);
		} else {
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("reminder.isnotpresent"), message.Reminder_isNotPresent);
		}
	}

	// set reminder repeat to daily option
	@Override
	public Response repeatDaily(String token, int noteid) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);
		List<NoteEntity> allNotedata = noteRepository.findAll();

		List<NoteEntity> listOfNotes = allNotedata.stream()
				.filter(userdata -> userdata.getUserEntity().getId() == user.getId()).collect(Collectors.toList());
		// check if note is present or not
		if (listOfNotes == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.notexist"), message.Note_Not_Exist);
		NoteEntity noteData = noteRepository.findById(noteid);
		ReminderEntity reminderData = noteData.getReminderEntity();
		// check if reminder is present or not
		if (reminderData == null)
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("reminder.isnotpresent"), message.Reminder_isNotPresent);
		// check if reminder repeat is set or not
		if ((reminderData.isMonthly() == false) && (reminderData.isWeekly() == false)
				&& (reminderData.isYearly() == false)) {
			reminderData.setDaily(true);
			reminderData.setDoNotRepeat(false);
			reminderRepository.save(reminderData);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("reminder.set.daily"), message.Reminder_SetToRepeat);
		} else {
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("reminder.notset"), message.Reminder_NotSet);
		}
	}

	// set reminder repeat to weekly option
	@Override
	public Response repeatWeekly(String token, int noteid) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);

		List<NoteEntity> allNotedata = noteRepository.findAll();

		List<NoteEntity> listOfNotes = allNotedata.stream()
				.filter(userdata -> userdata.getUserEntity().getId() == user.getId()).collect(Collectors.toList());

		if (listOfNotes == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.notexist"), message.Note_Not_Exist);
		NoteEntity noteData = noteRepository.findById(noteid);
		ReminderEntity reminderData = noteData.getReminderEntity();
		// check if reminder is present or not
		if (reminderData == null)
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("reminder.isnotpresent"), message.Reminder_isNotPresent);
		// check if reminder repeat is set or not
		if ((reminderData.isMonthly() == false) && (reminderData.isDaily() == false)
				&& (reminderData.isYearly() == false)) {
			reminderData.setWeekly(true);
			reminderData.setDoNotRepeat(false);
			reminderRepository.save(reminderData);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("reminder.set.weekly"), message.Reminder_SetToRepeat);
		} else {
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("reminder.notset"), message.Reminder_NotSet);
		}

	}

	// set reminder repeat to monthly option
	@Override
	public Response repeatMonthly(String token, int noteid) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);

		List<NoteEntity> allNotedata = noteRepository.findAll();
		List<NoteEntity> listOfNotes = allNotedata.stream()
				.filter(userdata -> userdata.getUserEntity().getId() == user.getId()).collect(Collectors.toList());

		if (listOfNotes == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.notexist"), message.Note_Not_Exist);
		NoteEntity noteData = noteRepository.findById(noteid);
		ReminderEntity reminderData = noteData.getReminderEntity();
		// check if reminder is present or not
		if (reminderData == null)
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("reminder.isnotpresent"), message.Reminder_isNotPresent);
		// check if reminder repeat is set or not
		if ((reminderData.isDaily() == false) && (reminderData.isWeekly() == false)
				&& (reminderData.isYearly() == false)) {
			reminderData.setMonthly(true);
			reminderRepository.save(reminderData);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("reminder.set.monthly"), message.Reminder_SetToRepeat);
		} else {
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("reminder.notset"), message.Reminder_NotSet);
		}
	}

	// set reminder repeat to yearly option
	@Override
	public Response repeatYearly(String token, int noteid) {
		String email = jwtOperation.getToken(token);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("status.email.notexist"), message.User_Not_Exist);

		List<NoteEntity> allNotedata = noteRepository.findAll();
		List<NoteEntity> listOfNotes = allNotedata.stream()
				.filter(userdata -> userdata.getUserEntity().getId() == user.getId()).collect(Collectors.toList());

		if (listOfNotes == null)
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("note.notexist"), message.Note_Not_Exist);
		NoteEntity noteData = noteRepository.findById(noteid);
		ReminderEntity reminderData = noteData.getReminderEntity();
		// check if reminder is present or not
		if (reminderData == null)
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("reminder.isnotpresent"), message.Reminder_isNotPresent);
		// check if reminder repeat is set or not
		if ((reminderData.isMonthly() == false) && (reminderData.isWeekly() == false)
				&& (reminderData.isDaily() == false)) {
			reminderData.setYearly(true);
			reminderData.setDoNotRepeat(false);
			reminderRepository.save(reminderData);
			return new Response(Integer.parseInt(environment.getProperty("status.success.code")),
					environment.getProperty("reminder.set.yearly"), message.Reminder_SetToRepeat);
		} else {
			return new Response(Integer.parseInt(environment.getProperty("status.bad.code")),
					environment.getProperty("reminder.notset"), message.Reminder_NotSet);
		}
	}
}
