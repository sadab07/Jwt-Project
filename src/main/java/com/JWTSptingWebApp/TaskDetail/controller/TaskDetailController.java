
package com.JWTSptingWebApp.TaskDetail.controller;



import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;


import com.JWTSptingWebApp.TaskDetail.Service.TaskDetailService;
import com.JWTSptingWebApp.TaskDetail.dto.TaskDetailDto;
import com.JWTSptingWebApp.taskmng.ServiceImpl.TaskMngServiceImpl;
import com.JWTSptingWebApp.taskmng.model.taskfilter;
import com.JWTSptingWebApp.taskmng.service.TaskmngService;

@RestController
@RequestMapping("/taskDetails")
public class TaskDetailController {
	@Autowired
	TaskDetailService taskDetailService;
	
	@Autowired
	TaskmngService taskmngService;
	
	@PostMapping("/create")
	public ResponseEntity<?> createTask(@RequestBody TaskDetailDto taskDetaildto){
		return taskDetailService.createTask(taskDetaildto);
	}


	@PostMapping("/toDo")
	public ResponseEntity<?> todo(@RequestBody taskfilter task){
			return taskmngService.todofileter(task); 
	}	
	@PostMapping("/inProgress")
	public ResponseEntity<?> inProgress(@RequestBody taskfilter task){
			return taskmngService.inProgressfileter(task); 
	}	
	@PostMapping("/completed")
	public ResponseEntity<?> completedfilter(@RequestBody taskfilter task){
			return taskmngService.completedFilter(task); 
	}	

	@PostMapping("/complete")
	public ResponseEntity<?> addFeebback(@RequestParam String taskToken,@RequestBody TaskDetailDto taskDetailDto){
		return taskDetailService.addFeedback(taskToken , taskDetailDto);
	}
	
	@PostMapping("/getFeedbackDetail")
	public ResponseEntity<?> getFeedbackDetail(){
		return taskDetailService.getFeedbackDetail();
	}
 }
