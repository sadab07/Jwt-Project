package com.JWTSptingWebApp.UserDetail.Dto;

import java.sql.Timestamp;

public class UserDetailDto {

	long userid;

	String firstName;
	String lastName;
	String email;
	String username;
	String contact;
	String designation;
	String bloodGroup;
	String gender;
	String address;
	String password;
	String confirmPassword;
	String tokenuser;
	String token;
	boolean active;
	

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	Timestamp createdTime;

	public UserDetailDto(long userid, String firstName, String lastName, String email, String username, String contact,
			String designation, String bloodGroup, String gender, String address, String password,
			String confirmPassword, String token, Timestamp createdTime, Timestamp updateTime,boolean active) {
		super();
		this.userid = userid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.contact = contact;
		this.designation = designation;
		this.bloodGroup = bloodGroup;
		this.gender = gender;
		this.address = address;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.token = token;
		this.createdTime = createdTime;
		this.updateTime = updateTime;
		this.active=active;
		
	}

	Timestamp updateTime;

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

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

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserDetailDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "UserDetailDto [userid=" + userid + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", username=" + username + ", contact=" + contact + ", designation=" + designation
				+ ", bloodGroup=" + bloodGroup + ", gender=" + gender + ", address=" + address + ", password="
				+ password + ", confirmPassword=" + confirmPassword + ", tokenuser=" + tokenuser + ", token=" + token
				+ ", createdTime=" + createdTime + ", updateTime=" + updateTime + "]";
	}

	public UserDetailDto(long userid, String firstName, String lastName, String email, String username, String contact,
			String designation, String bloodGroup, String gender, String address, String password,
			String confirmPassword, String tokenuser, String token, Timestamp createdTime,
			Timestamp updateTime) {
		super();
		this.userid = userid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.contact = contact;
		this.designation = designation;
		this.bloodGroup = bloodGroup;
		this.gender = gender;
		this.address = address;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.tokenuser = tokenuser;
		this.token = token;
		
		this.createdTime = createdTime;
		this.updateTime = updateTime;
	}

	public String getTokenuser() {
		return tokenuser;
	}

	public void setTokenuser(String tokenuser) {
		this.tokenuser = tokenuser;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
