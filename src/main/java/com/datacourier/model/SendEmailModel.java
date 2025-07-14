package com.datacourier.model;

import java.util.List;

public class SendEmailModel {
	
	private String email;
	
	private List<Files> files;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Files> getFiles() {
		return files;
	}
	public void setFiles(List<Files> files) {
		this.files = files;
	}
	

}
