
package com.JWTSptingWebApp.UserDetail.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.JWTSptingWebApp.MettingMng.Model.Metting;

@Entity
@Table
public class UserDetail {

	public UserDetail() {
		super();

	}

	public UserDetail(UserDetail u, Metting metting) {
		this.userid = u.userid;
		this.firstName = u.firstName;
		this.lastName = u.lastName;
		this.email = u.email;
		this.username = u.username;
		this.contact = u.contact;
		this.designation = u.designation;
		this.bloodGroup = u.bloodGroup;
		this.gender = u.gender;
		this.address = u.address;
		this.password = u.password;
		this.token = u.token;
		this.createdTime = u.createdTime;
		this.updateTime = u.updateTime;
		this.enabled = u.enabled;
		this.lastlogin = u.lastlogin;
		this.resettoken = u.resettoken;
		this.role = u.role;
		this.tokencreationdate = u.tokencreationdate;

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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}

	public String getResettoken() {
		return resettoken;
	}

	public void setResettoken(String resettoken) {
		this.resettoken = resettoken;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDateTime getTokencreationdate() {
		return tokencreationdate;
	}

	public void setTokencreationdate(LocalDateTime tokencreationdate) {
		this.tokencreationdate = tokencreationdate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long userid;
	@Column
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

	String token;
	String tokenuser;
	Timestamp createdTime;

	Timestamp updateTime;
	
	boolean active;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "lastlogin")
	private Date lastlogin;

	@Column(name = "resettoken")
	private String resettoken;

	@Column(name = "role")
	private String role;

	@Column(columnDefinition = "TIMESTAMP")

	private LocalDateTime tokencreationdate;

	public String getTokenuser() {
		return tokenuser;
	}

	public void setTokenuser(String tokenuser) {
		this.tokenuser = tokenuser;
	}

	@Override
	public String toString() {
		return "UserDetail [userid=" + userid + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", username=" + username + ", contact=" + contact + ", designation=" + designation
				+ ", bloodGroup=" + bloodGroup + ", gender=" + gender + ", address=" + address + ", password="
				+ password + ", token=" + token + ", tokenuser=" + tokenuser + ", createdTime=" + createdTime
				+ ", updateTime=" + updateTime + ", enabled=" + enabled + ", lastlogin=" + lastlogin + ", resettoken="
				+ resettoken + ", role=" + role + ", tokencreationdate=" + tokencreationdate + "]";
	}

	public UserDetail(long userid, String firstName, String lastName, String email, String username, String contact,
			String designation, String bloodGroup, String gender, String address, String password, String token,
			String tokenuser, Timestamp createdTime, Timestamp updateTime, boolean enabled, Date lastlogin,
			String resettoken, String role, LocalDateTime tokencreationdate,boolean active) {
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
		this.token = token;
		this.tokenuser = tokenuser;
		this.createdTime = createdTime;
		this.updateTime = updateTime;
		this.enabled = enabled;
		this.lastlogin = lastlogin;
		this.resettoken = resettoken;
		this.role = role;
		this.tokencreationdate = tokencreationdate;
		this.active=active;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
