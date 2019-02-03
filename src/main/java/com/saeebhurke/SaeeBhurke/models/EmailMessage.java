package com.saeebhurke.SaeeBhurke.models;

public class EmailMessage {

	private String subject;
	private String body;
	// Attachments feature not introduced yet
	// private ArrayList<MultipartFile> attachments;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
