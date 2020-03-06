package com.bridgelabz.fundoonotesapi.services;

import com.bridgelabz.fundoonotesapi.dto.LabelDto;
import com.bridgelabz.fundoonotesapi.response.Response;

/**
 * @author Tejashree Surve
 * @Purpose : This is LabelService Interface which contains every method of services class.
 */
public interface ILabelService {
	// add label
	Response createLabel(String token, LabelDto labelDto);
	
	// get all label 
	Response getAllLabel(String token);
	
	// update label
	Response editLabel(String token, int labelId, LabelDto labelDto);
	
	// delete label
	Response deleteLabel(String token, int labelId);
	
	// get label by id
	Response getNoteByLabelName(String token, int labelId);
	
	// sort label by title
	Response sortLabelByTitle(String token);
}
