package com.bridgelabz.fundoonotesapi.response;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Tejashree Surve
 * @Purpose : This Class provide the data to send in http response.
 */
public class Response {
	private int statuscode;
	private String message;
	private Object data;

	@Autowired
	public Response(int statuscode, String message, Object data) {
		this.statuscode = statuscode;
		this.message = message;
		this.data = data;
	}

	public int getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
