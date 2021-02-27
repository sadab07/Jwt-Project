package com.JWTSptingWebApp.TaskDetail.Service;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.JWTSptingWebApp.TaskDetail.dto.TaskDetailDto;
import com.JWTSptingWebApp.TaskDetail.model.Response;
import com.JWTSptingWebApp.TaskDetail.model.TaskDetail;
import com.JWTSptingWebApp.TaskDetail.model.TaskFeedbackDetail;
import com.JWTSptingWebApp.TaskDetail.repository.TaskDetailRepository;
import com.JWTSptingWebApp.TaskDetail.repository.TaskFeedbackDetailRepository;


@Service
public class TaskDetailImplService implements TaskDetailService{
	
	
	@Autowired
	TaskDetailRepository taskDetailRepo;
	
	@Autowired
	TaskFeedbackDetailRepository taskFeedbackDetailRepo;
	
	ModelMapper modelMapper = new ModelMapper();
	Response response = new Response();
	
	private static String generateRandom() {
		String aToZ="01234ABCDEFGHIJKLMNO012345PQRSTUVWXYZ678901235abcdefghijklmnopqrstuvwxyz6789";
	    
		Random rand=new Random();
	    StringBuilder res=new StringBuilder();
	    
	    for (int i = 0; i < 8; i++) {
	    	int randIndex=rand.nextInt(aToZ.length());
	    	res.append(aToZ.charAt(randIndex));
	        }
		return	res.toString();
	}
	
	@Override
	public ResponseEntity<?> createTask(TaskDetailDto taskDetaildto) {
		
		String statusText = taskDetaildto.getStatusText();
		if(statusText==null || statusText=="")
		{

			taskDetaildto.setStatusText("Not Started");
			statusText = taskDetaildto.getStatusText();
		}
		if(statusText.equals("Not Started") || statusText.equals("Waiting for Someone Else") || statusText.equals("In Progress")
				|| statusText.equals("Completed"))
		{
		
		taskDetaildto.setTaskToken(generateRandom());
		
		ModelMapper modelMapper = new ModelMapper();
		TaskDetail taskDetail = modelMapper.map(taskDetaildto, TaskDetail.class);
		
		taskDetailRepo.save(taskDetail);
		
		LinkedHashMap<String, Object> hashMap = new LinkedHashMap<String , Object>();
		
		hashMap.put("subject" , taskDetail.getSubject());
		hashMap.put("projectName" , taskDetail.getProjectName());
		hashMap.put("priority" , taskDetail.getPriority());
		hashMap.put("assignedTo" , taskDetail.getAssignedTo());
		hashMap.put("statusText" , taskDetail.getStatusText());
		hashMap.put("statusPercentage" , taskDetail.getStatusPercentage());
		hashMap.put("taskToken", taskDetail.getTaskToken());
		hashMap.put("startDate" , taskDetail.getStartDate());
		hashMap.put("dueDate" , taskDetail.getDueDate());
		//hashMap.put("attachments", taskDetail.getAttachments()); 
		hashMap.put("verifiedBy" , taskDetail.getVerifiedBy());
		
		return ResponseEntity.ok(response.addMap(hashMap));
		}
		else{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("status text is not matched!!!");
		}
	}

	

	@Override
	public ResponseEntity<?> addFeedback(String taskToken, TaskDetailDto taskDetailDto) {
		TaskFeedbackDetail taskFeedbackDetail = modelMapper.map(taskDetailDto, TaskFeedbackDetail.class);
		taskFeedbackDetail.setTaskToken(taskToken);
		taskFeedbackDetailRepo.save(taskFeedbackDetail);
		
		return ResponseEntity.ok(taskFeedbackDetail);
	}

	
	
	@Override
	public ResponseEntity<?> getFeedbackDetail() {
		List<TaskFeedbackDetail> taskFeedbackDetail = taskFeedbackDetailRepo.findAll();
		return ResponseEntity.ok(response.addFeedbackMap(taskFeedbackDetail));
	}
}
