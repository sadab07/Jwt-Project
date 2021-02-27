package com.JWTSptingWebApp.TaskDetail.dto;

import java.sql.Date;

public class TaskDetailDto {
	
	private String taskToken;
	private String subject;
	private String projectName;
	private String priority;
	private String assignedTo;
	private String statusText;
	private int statusPercentage;
	private Date startDate;
	private Date dueDate;
	private String attachments;
	private String description;
	private String verifiedBy;
	private String roadblock;
	private String learning;
	private String feedback;
	private boolean happy;

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

	public String getRoadblock() {
		return roadblock;
	}

	public void setRoadblock(String roadblock) {
		this.roadblock = roadblock;
	}

	public String getLearning() {
		return learning;
	}

	public void setLearning(String learning) {
		this.learning = learning;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public boolean isHappy() {
		return happy;
	}

	public void setHappy(boolean happy) {
		this.happy = happy;
	}

	@Override
	public String toString() {
		return "TaskDetailDto [taskToken=" + taskToken + ", subject=" + subject + ", projectName=" + projectName
				+ ", priority=" + priority + ", assignedTo=" + assignedTo + ", statusText=" + statusText
				+ ", statusPercentage=" + statusPercentage + ", startDate=" + startDate + ", dueDate=" + dueDate
				+ ", attachments=" + attachments + ", description=" + description + ", verifiedBy="
				+ verifiedBy + ", roadblock=" + roadblock + ", learning=" + learning + ", feedback=" + feedback
				+ ", happy=" + happy + "]";
	}
	
}

