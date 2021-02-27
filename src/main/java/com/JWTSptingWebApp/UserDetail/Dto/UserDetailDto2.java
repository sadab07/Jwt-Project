package com.JWTSptingWebApp.UserDetail.Dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.JWTSptingWebApp.UserDetail.model.UserDetail;

public class UserDetailDto2 {

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	String firstName;
	String lastName;
	String email;
	String username;
	String contact;
	String designation;
	Timestamp createdTime;

	public String getCreatedTime() {

		String pattern = "dd MMMM yyyy,HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String dateTime = simpleDateFormat.format(createdTime);
		return dateTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	String tokenuser;

	public UserDetailDto2(UserDetail userDetail) {

		this.firstName = userDetail.getFirstName();
		this.lastName = userDetail.getLastName();
		this.email = userDetail.getEmail();
		this.username = userDetail.getUsername();
		this.contact = userDetail.getContact();
		this.designation = userDetail.getDesignation();
		this.tokenuser = userDetail.getTokenuser();
		this.createdTime = userDetail.getCreatedTime();

	}

	public UserDetailDto2() {

	}

	public UserDetailDto2(String firstName, String lastName, String email, String username, String contact,
			String designation, Timestamp createdTime, String tokenuser) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.contact = contact;
		this.designation = designation;
		this.createdTime = createdTime;

		this.tokenuser = tokenuser;
	}

	public String getTokenuser() {
		return tokenuser;
	}

	public void setTokenuser(String tokenuser) {
		this.tokenuser = tokenuser;
	}

	@Override
	public String toString() {
		return "UserDetailDto2 [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", username="
				+ username + ", contact=" + contact + ", designation=" + designation + ", createdTime=" + createdTime
				+ ", tokenuser=" + tokenuser + "]";
	}

}
