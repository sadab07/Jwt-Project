//package com.JWTSptingWebApp.springbootsecurity.model;
//
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotEmpty;
//
//@Entity
//@Table(name = "userdetail")
//public class UserDetail {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id", nullable = false, updatable = false)
//	private long id;
//
//	@Column(name = "email", nullable = false, unique = true)
//	@Email(message = "Please provide a valid e-mail")
//	@NotEmpty(message = "Please provide an e-mail")
//	private String email;
//
//	@NotEmpty(message = "Please provide your password")
//	@Column(name = "password", nullable = false)
//	private String password;
//
//	@Column(name = "firstname")
//	@NotEmpty(message = "Please provide your first name")
//	private String firstname;
//
//	@Column(name = "lastname")
//	@NotEmpty(message = "Please provide your last name")
//	private String lastname;
//
//	@Column(name = "enabled")
//	private boolean enabled;
//
//	@Column(columnDefinition = "TIMESTAMP")
//	private Timestamp createdon;
//
//	@Column(name = "lastlogin")
//	private Date lastlogin;
//
//	@Column(name = "resettoken")
//	private String resettoken;
//
//	@NotEmpty(message = "Please provide your username")
//	@Column(name = "username")
//	private String username;
//
//	@Column(name = "role")
//	private String role;
//
//	@Column(name = "token")
//	private String token;
//
//	@Column(columnDefinition = "TIMESTAMP")
//	private LocalDateTime tokencreationdate;
//
//	@Column(name = "contactno")
//	private String contactno;
//
//	@Column(name = "designation")
//	private String designation;
//
//	@Column(name = "bloodgrp")
//	private String bloodgrp;
//
//	@Column(name = "gender")
//	private String gender;
//
//	@Column(name = "address")
//	private String address;
//
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String getFirstname() {
//		return firstname;
//	}
//
//	public void setFirstname(String firstname) {
//		this.firstname = firstname;
//	}
//
//	public String getLastname() {
//		return lastname;
//	}
//
//	public void setLastname(String lastname) {
//		this.lastname = lastname;
//	}
//
//	public boolean isEnabled() {
//		return enabled;
//	}
//
//	public void setEnabled(boolean enabled) {
//		this.enabled = enabled;
//	}
//
//	public Timestamp getCreatedon() {
//		return createdon;
//	}
//
//	public void setCreatedon(Timestamp createdon) {
//		this.createdon = createdon;
//	}
//
//	public Date getLastlogin() {
//		return lastlogin;
//	}
//
//	public void setLastlogin(Date lastlogin) {
//		this.lastlogin = lastlogin;
//	}
//
//	public String getResettoken() {
//		return resettoken;
//	}
//
//	public void setResettoken(String resettoken) {
//		this.resettoken = resettoken;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public String getRole() {
//		return role;
//	}
//
//	public void setRole(String role) {
//		this.role = role;
//	}
//
//	public String getToken() {
//		return token;
//	}
//
//	public void setToken(String token) {
//		this.token = token;
//	}
//
//	public LocalDateTime getTokencreationdate() {
//		return tokencreationdate;
//	}
//
//	public void setTokencreationdate(LocalDateTime tokencreationdate) {
//		this.tokencreationdate = tokencreationdate;
//	}
//
//	public String getContactno() {
//		return contactno;
//	}
//
//	public void setContactno(String contactno) {
//		this.contactno = contactno;
//	}
//
//	public String getDesignation() {
//		return designation;
//	}
//
//	public void setDesignation(String designation) {
//		this.designation = designation;
//	}
//
//	public String getBloodgrp() {
//		return bloodgrp;
//	}
//
//	public void setBloodgrp(String bloodgrp) {
//		this.bloodgrp = bloodgrp;
//	}
//
//	public String getGender() {
//		return gender;
//	}
//
//	public void setGender(String gender) {
//		this.gender = gender;
//	}
//
//	public String getAddress() {
//		return address;
//	}
//
//	public void setAddress(String address) {
//		this.address = address;
//	}
//
//}
