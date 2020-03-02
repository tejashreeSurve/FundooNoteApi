package com.bridgelabz.fundoonotesapi.services;

import com.bridgelabz.fundoonotesapi.dto.ReminderDto;
import com.bridgelabz.fundoonotesapi.response.Response;

/**
 * @author Tejashree Surve 
 * @Purpose : This is ReminderService Interface which contains every method of services class.
 */
public interface IReminderService {

	// Add Reminder
	Response addReminder(String token, int noteId, ReminderDto reminderDto);

	// Show Reminder
	Response getReminder(String token);

	// Update Reminder
	Response updateReminder(String token, int noteId, ReminderDto reminderDto);

	// Delete Reminder
	Response deleteReminder(String token, int noteId);

	// Repeat Reminder Daily
	Response repeatDaily(String token, int noteId);

	// Repeat Reminder Weekly
	Response repeatWeekly(String token, int noteId);

	// Repeat Reminder Monthly
	Response repeatMonthly(String token, int noteId);

	// Repeat Reminder Yearly
	Response repeatYearly(String token, int noteId);

}
