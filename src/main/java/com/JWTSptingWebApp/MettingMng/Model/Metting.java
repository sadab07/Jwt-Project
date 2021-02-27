package com.JWTSptingWebApp.MettingMng.Model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;

import org.springframework.stereotype.Service;


@Entity
@Table(name = "Metting")
public class Metting {
	@Id
	private String token;
	private String subject;
	private String description;
	private String projectname;
	Timestamp starttime;
	Timestamp endtime;
	
//	String pattern = "dd MMMM yyyy,HH:mm";
//	 SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//	 String startTime = simpleDateFormat.format(starttime);
	 
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public Timestamp getStarttime() {
		return starttime;
	}
	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}
	public Timestamp getEndtime() {
		return endtime;
	}
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}
	@Override
	public String toString() {
		return "Metting [token=" + token + ", subject=" + subject + ", description=" + description + ", projectname="
				+ projectname + ", starttime=" + starttime + ", endtime=" + endtime + ", getToken()=" + getToken()
				+ ", getSubject()=" + getSubject() + ", getDescription()=" + getDescription() + ", getProjectname()="
				+ getProjectname() + ", getStarttime()=" + getStarttime() + ", getEndtime()=" + getEndtime()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	public Metting() {
	}
	
	public Metting(String token, String subject, String description, String projectname, Timestamp starttime,
			Timestamp endtime) {
		super();
		this.token = token;
		this.subject = subject;
		this.description = description;
		this.projectname = projectname;
		this.starttime = starttime;
		this.endtime = endtime;
		
	}
	
}
