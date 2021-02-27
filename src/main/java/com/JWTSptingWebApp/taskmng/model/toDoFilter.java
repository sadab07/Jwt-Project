package com.JWTSptingWebApp.taskmng.model;

import java.sql.Date;


import com.fasterxml.jackson.annotation.JsonProperty;

public class toDoFilter {
	@JsonProperty("subject")
	public String subject;
	@JsonProperty("assignedTo")
	public String assignedTo;
	@JsonProperty("projectName")
	private String projectName;
	@JsonProperty("priority")
	private String priority;
//	@JsonProperty("startDate")
	private Date startDate;
//	@JsonProperty("dueDate")
	private Date dueDate;
	@JsonProperty("verifiedBy")
	private String verifiedBy;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getVerifiedBy() {
		return verifiedBy;
	}
	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}
	
}
