package com.JWTSptingWebApp.taskmng.service;

import org.springframework.http.ResponseEntity;

import com.JWTSptingWebApp.taskmng.model.taskfilter;

public interface TaskmngService {
//	public ResponseEntity<?> getAllTasks(taskfilter taskfilter);
	public ResponseEntity<?> ListAllTasks(taskfilter taskfilter);
//	public ResponseEntity<?> ListAllTask();
	public ResponseEntity<?> todofileter(taskfilter taskfilter);
	public ResponseEntity<?> inProgressfileter(taskfilter taskfilter);
	public ResponseEntity<?> completedFilter(taskfilter taskfilter);
}
