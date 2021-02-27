package com.JWTSptingWebApp.TaskDetail.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TaskFeedbackDetail {
	
	@Id
	@GeneratedValue
	@JsonIgnore
	private int feedbackId;
	private String roadblock;
	private String learning;
	private String feedback;
	@Column(columnDefinition = "BOOLEAN DEFAULT true")
	private boolean happy;
	private String taskToken;
	
	public TaskFeedbackDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TaskFeedbackDetail(int feedbackId, String roadblock, String learning, String feedback, boolean happy,
			String taskToken) {
		super();
		this.feedbackId = feedbackId;
		this.roadblock = roadblock;
		this.learning = learning;
		this.feedback = feedback;
		this.happy = happy;
		this.taskToken = taskToken;
	}
	
	public int getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}
	public String getRoadblock() {
		return roadblock;
	}
	public void setRoadblock(String roadblock) {
		this.roadblock = roadblock;
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
		return "TaskFeedbackDetail [feedbackId=" + feedbackId + ", roadblock=" + roadblock + ", learning=" + learning
				+ ", feedback=" + feedback + ", happy=" + happy + ", taskToken=" + taskToken + "]";
	}
	public String getTaskToken() {
		return taskToken;
	}
	public void setTaskToken(String taskToken) {
		this.taskToken = taskToken;
	}



	public String getLearning() {
		return learning;
	}



	public void setLearning(String learning) {
		this.learning = learning;
	}

	
}
