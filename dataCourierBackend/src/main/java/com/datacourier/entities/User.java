package com.datacourier.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	private String userName;

	private String userType;

	private String emailAddress;
	
	private String toNumber;

	@Column(insertable = false)
	private Date emailSentDate;

	public User() {
		super();
	}

	public User(String userName, String emailAddress, String userType) {
		super();
		this.userName = userName;
		this.emailAddress = emailAddress;
		this.userType = userType;
	}
	public User(String userName, String toNumber, String userType,String emailAddress) {
		super();
		this.userName = userName;
		this.toNumber = toNumber;
		this.userType = userType;
		this.emailAddress = emailAddress;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getEmailSentDate() {
		return emailSentDate;
	}

	public String getToNumber() {
		return toNumber;
	}

	public void setToNumber(String toNumber) {
		this.toNumber = toNumber;
	}

	public void setEmailSentDate(Date emailSentDate) {
		this.emailSentDate = emailSentDate;
	}

	
	
}
