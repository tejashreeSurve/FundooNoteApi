package com.bridgelabz.fundoonotesapi.services;

import com.bridgelabz.fundoonotesapi.dto.ChangeLabelDto;
import com.bridgelabz.fundoonotesapi.dto.LabelDto;
import com.bridgelabz.fundoonotesapi.response.Response;

/**
 * @author Tejashree Surve
 * @Purpose : This is LabelService Interface which contains every method of services class.
 */
public interface ILabelService {
	// add label
	Response addLabel(String token, int id,LabelDto labeldto);
	
	// get all label 
	Response getAlllabel(String token);
	
	// update label
	Response updateLabel(String token,int id, LabelDto labeldto);
	
	// delete label
	Response deleteLabel(String token, int id);
	
	// get label by id
	Response getNoteByLabelId(int id);
	
	// get label by label name
	Response getNoteByLabelName(LabelDto labeldto);
	
	// change label
	Response changeLabel(ChangeLabelDto changelabel);
	
	// sort label by title
	Response sortLabelByTitle(String token);
	
	// sort all label by title
	Response sortAllLabel();
}
