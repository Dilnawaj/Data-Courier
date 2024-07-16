package com.datacourier.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FileData {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Long userId;
	
	private Long reqId;
	
	private Boolean fileStatus;
	
	private String fileNames;
	
	private  String FileType;
	
	

	public FileData() {
		super();
	}

	public FileData(Long userId, String fileNames, String fileType) {
		super();
		this.userId = userId;
		this.fileNames = fileNames;
		FileType = fileType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFileNames() {
		return fileNames;
	}

	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}

	public String getFileType() {
		return FileType;
	}

	public void setFileType(String fileType) {
		FileType = fileType;
	}

	public Long getReqId() {
		return reqId;
	}

	public void setReqId(Long reqId) {
		this.reqId = reqId;
	}

	public Boolean getFileStatus() {
		if(fileStatus==null)
		{
			return false;
		}
		return fileStatus;
	}

	public void setFileStatus(Boolean fileStatus) {
		this.fileStatus = fileStatus;
	}
	
	

	

}
