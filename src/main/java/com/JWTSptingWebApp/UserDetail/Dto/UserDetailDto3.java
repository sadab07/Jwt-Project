package com.JWTSptingWebApp.UserDetail.Dto;

public class UserDetailDto3 {

	String firstName;

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

	String lastName;
	String email;
	String username;
	String contact;
	String designation;
	String bloodGroup;
	String gender;
	String address;

	String tokenuser;

	public UserDetailDto3() {

	}

	public String getTokenuser() {
		return tokenuser;
	}

	public void setTokenuser(String tokenuser) {
		this.tokenuser = tokenuser;
	}

	public UserDetailDto3(String firstName, String lastName, String email, String username, String contact,
			String designation, String bloodGroup, String gender, String address, String tokenuser) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.contact = contact;
		this.designation = designation;
		this.bloodGroup = bloodGroup;
		this.gender = gender;
		this.address = address;
		this.tokenuser = tokenuser;
	}

	@Override
	public String toString() {
		return "UserDetailDto3 [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", username="
				+ username + ", contact=" + contact + ", designation=" + designation + ", bloodGroup=" + bloodGroup
				+ ", gender=" + gender + ", address=" + address + ", tokenuser=" + tokenuser + "]";
	}

}
