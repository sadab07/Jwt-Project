package com.JWTSptingWebApp.TaskDetail.Service;

import org.springframework.http.ResponseEntity;

import com.JWTSptingWebApp.TaskDetail.dto.TaskDetailDto;


public interface TaskDetailService {

	ResponseEntity<?> createTask(TaskDetailDto taskDetaildto);

	ResponseEntity<?> addFeedback(String taskToken , TaskDetailDto taskDetailDto);

	ResponseEntity<?> getFeedbackDetail();


	


}
