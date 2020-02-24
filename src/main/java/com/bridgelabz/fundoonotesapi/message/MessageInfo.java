package com.bridgelabz.fundoonotesapi.message;

import org.springframework.stereotype.Component;

/**
 * @author Tejashree Surve
 * @Purpose : This is class which provide message to Response Entity.
 */
@Component
public class MessageInfo {
	
	/********************** message for Http status code *********************/
	
	public String Bad_Request = "400";
	
	public String Success_Request = "200";
	
	public String Redirect_Request = "300";
	
	/********************** messages for user ************************/
	
	public String Invalide_Token = "Invalide Token";

	public String User_Exist = "Please try with other Email-id";

	public String Registration_Done = "Befor Login Please Verify Email-id ";

	public String Login_Done = "Loged-In Successfully";

	public String Invalide_Password = "Please Try with other Password";

	public String User_Not_Exist = "Please try with another Email-id";

	public String Token_Send = "Please verify Token for Forget Password";

	public String User_Not_Verify = "Please first Verify Email-id by token";

	public String Update_Password = "User can login with new Password";

	public String Verify_User = "User can Login Successfully";

	/************************ messages for notes *************************/
	
	public String Note_Not_Exist = "Note does not Exist";
	
	public String Note_UnPin = "You can Pin it again";
	
	public String Note_Pin = "You can UnPin it again";
	
	public String Note_Archive = "You can UnAchive it again";
	
	public String Note_UnArchive = "You can Archive it agin";
	
	public String Note_UnTrash = "You can Trash it again";
	
	public String Note_Trash = "You can UnTrash it again";
	
	public String Note_Label_Not_Exist = "No Note with this Label";
	
	public String Note_Create = "Note is Successfully create for given User token";
	
	public String Note_Update = "Note is Update Successfully";
	
	public String Note_Delete = "Note is Deleted from Note List";
	
	/************************ messages for label *************************/
	
	public String Label_Not_Exist = "No Label present with this Id";
	
	public String Label_Change = "Label Name is been changed";
	
	public String Label_Create = "Label is Successfully created in given Note";
	
	public String Label_Update = "Label is Successfully updated";
	
	public String Label_Delete = "You can create/add new Label in Note";
	
	/************************* messages for Reminder *************************/
	
	public String Reminder_isPresent = "Cannot Add Reminder";
	
	public String Reminder_isNotPresent = "Please First add Reminder to Note";
	
	public String Reminder_isSet = "Reminder is Set Successfully";
	
	public String Reminder_isUpdate = "Reminder is Updated Successfully with given details";
	
	public String Reminder_Delete = "You can set another Reminder";
	
	public String Reminder_SetToRepeat = "Reminder is Set to Repeat";
	
	public String Reminder_NotSet = "Reminder is not abel to Set";
	
	/************************* message for collaborator ***********************/
	
	public String Collaborator = "You can add new Collaborator";
	
	/************************* Profile Pic ***********************************/
	
	public String File_Is_Empty = "Please select the Profile_Pic";
	
	public String File_Not_Upload = "Profile Pic cannot be uploaded,Please try again !!";
	
	public String Profile_Uploaded = "Profile is successfully uploaded";
	
}
