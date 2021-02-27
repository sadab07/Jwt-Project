package com.JWTSptingWebApp.TaskDetail.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Response {

	public static void main(String args[]) {
	
	}
	public HashMap<String, Object> addMap(LinkedHashMap<String,Object> hashMap){
			
		LinkedHashMap<String, Object> map = new LinkedHashMap<String , Object>();
		map.put("code", 200);
		map.put("status", true);
		map.put("message", "Create Task");
		map.put("data", hashMap);
		return map;
	}
	
	public HashMap<String, Object> addFeedbackMap(List<TaskFeedbackDetail> taskFeedbackDetail){
		
		LinkedHashMap<String, Object> map = new LinkedHashMap<String , Object>();
		map.put("code", 200);
		map.put("status", true);
		map.put("message", "Tasks FeedBacks");
		map.put("data", taskFeedbackDetail);
		return map;
	}
}
