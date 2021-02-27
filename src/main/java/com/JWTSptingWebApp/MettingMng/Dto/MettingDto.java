package com.JWTSptingWebApp.MettingMng.Dto;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

@Service
public class MettingDto {
	
	private String token;
	private String subject;
	private String description;
	private String projectname;
	
	Timestamp starttime;
	Timestamp endtime;

	
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
	public MettingDto(String token, String subject, String description, String projectname, Timestamp startTime2,
			Timestamp endTime2) {
		
		this.token = token;
		this.subject = subject;
		this.description = description;
		this.projectname = projectname;
		this.starttime = startTime2;
		this.endtime = endTime2;
	}
	public MettingDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
