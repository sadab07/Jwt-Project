package com.JWTSptingWebApp.TaskDetail.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;


@Entity
@Table(name = "taskDetails")

public class TaskDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String taskToken;
	private String subject;
	private String projectName;
	@Column(columnDefinition = "String Default 'Normal'")
	private String priority;
	private String assignedTo;
	@Column(columnDefinition = "String Default 'Not Started'")
	private String statusText;
	@Column(columnDefinition = "int default 0")
	private int statusPercentage;
	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	@Column(name= "startDate")
	private Date startDate;
	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	@Column(name= "dueDate")
	private Date dueDate;

	private String attachments;

	@Column(length = 1024)
	private String description;
	private String verifiedBy;
	public String getTaskToken() {
		return taskToken;
	}
	public void setTaskToken(String taskToken) {
		this.taskToken = taskToken;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
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
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public int getStatusPercentage() {
		return statusPercentage;
	}
	public void setStatusPercentage(int statusPercentage) {
		this.statusPercentage = statusPercentage;
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
	public String getAttachments() {
		return attachments;
	}
	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVerifiedBy() {
		return verifiedBy;
	}
	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}
	@Override
	public String toString() {
		return "TaskDetail [taskToken=" + taskToken + ", subject=" + subject + ", projectName=" + projectName
				+ ", priority=" + priority + ", assignedTo=" + assignedTo + ", statusText=" + statusText
				+ ", statusPercentage=" + statusPercentage + ", startDate=" + startDate + ", dueDate=" + dueDate
				+ ", attachments=" + attachments + ", description=" + description + ", verifiedBy="
				+ verifiedBy + "]";
	}
}

