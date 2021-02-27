package com.JWTSptingWebApp.taskmng.ServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.JWTSptingWebApp.TaskDetail.model.TaskDetail;
import com.JWTSptingWebApp.TaskDetail.repository.TaskDetailRepository;
import com.JWTSptingWebApp.taskmng.model.taskfilter;
import com.JWTSptingWebApp.taskmng.service.TaskmngService;

@Service
public class TaskMngServiceImpl implements TaskmngService {
	
	@Autowired
	TaskDetailRepository taskDetailRepo;
	
	List<TaskDetail> getData=null;
	
	
	public ResponseEntity<?> todofileter(taskfilter taskfilter){
		Map<String,Object> innermap = new LinkedHashMap<String , Object>();
		Map<String,Object> map = new LinkedHashMap<String , Object>();
		List<Map<String, Object>> todolist1=new ArrayList<Map<String, Object>>();

		try{
		String sub=taskfilter.toDoFilter.getSubject();
		String pro=taskfilter.toDoFilter.getProjectName();
		String veri=taskfilter.toDoFilter.getVerifiedBy();
		String assign=taskfilter.toDoFilter.getAssignedTo();
		String prio=taskfilter.toDoFilter.getPriority();
		System.out.println(sub+pro);
		
//		pro=(pro.isEmpty() ?(pro=""):(pro;innermap.put("subject", pro)));
		if(pro==null||pro.isEmpty())
			pro="";
		else
			innermap.put("projectName", pro);
		
		if(sub==null||sub.isEmpty())
			sub="";
		else
			innermap.put("subject", sub);
		
		if(veri==null||veri.isEmpty())
			veri="";
		else
			innermap.put("verifiedBy", veri);
		
		if(prio==null||prio.isEmpty())
			prio="";
		else
			innermap.put("priority", prio);
		
		if(assign==null||assign.isEmpty())
			assign="";
		else
			innermap.put("assignedTo", assign);
		
		List<TaskDetail> getData1=taskDetailRepo.findForTodo(sub, pro,veri,assign,prio);
		System.out.println(pro);
//		.stream()		
//		.filter(p->p.getStatusText().equals("Not Started"))
//		.filter(p->p.getSubject().contains(sub))
//		.filter(p->p.getProjectName().contentEquals(pro)).collect(Collectors.toList());
		 
		for(TaskDetail oneData:getData1)
		{	
				todolist1.add(AllData(oneData));
		}
		map.put("toDofilter",innermap);
		map.put("code", 200);
		map.put("status", true);
		map.put("message", "Todo Data");
		map.put("data", todolist1);
		return ResponseEntity.ok(map);
		 	}
		catch (java.lang.NullPointerException e) {
		 		List<TaskDetail> getData1=taskDetailRepo.findAll();
				 
				for(TaskDetail oneData:getData1)
				{	
						if(oneData.getStatusText().equals("Not Started")) {
								todolist1.add(AllData(oneData));
						}
				}
				map.put("toDofilter",innermap);
				map.put("code", 200);
				map.put("status", true);
				map.put("message", "Todo Data");
				map.put("data", todolist1);
				return ResponseEntity.ok(map);
			}
	}
	
	
	public ResponseEntity<?> inProgressfileter(taskfilter taskfilter){
		Map<String,Object> innermap = new LinkedHashMap<String , Object>();
		Map<String,Object> map = new LinkedHashMap<String , Object>();
		List<Map<String, Object>> todolist1=new ArrayList<Map<String, Object>>();

		try{
		String sub=taskfilter.inProgressFilter.getSubject();
		String pro=taskfilter.inProgressFilter.getProjectName();
		String veri=taskfilter.inProgressFilter.getVerifiedBy();
		String assign=taskfilter.inProgressFilter.getAssignedTo();
		String prio=taskfilter.inProgressFilter.getPriority();
		System.out.println(sub+pro);
		
//		pro=(pro.isEmpty() ?(pro=""):(pro;innermap.put("subject", pro)));
		if(pro==null||pro.isEmpty())
			pro="";
		else
			innermap.put("projectName", pro);
		
		if(sub==null||sub.isEmpty())
			sub="";
		else
			innermap.put("subject", sub);
		
		if(veri==null||veri.isEmpty())
			veri="";
		else
			innermap.put("verifiedBy", veri);
		
		if(prio==null||prio.isEmpty())
			prio="";
		else
			innermap.put("priority", prio);
		
		if(assign==null||assign.isEmpty())
			assign="";
		else
			innermap.put("assignedTo", assign);
		
		List<TaskDetail> getData1=taskDetailRepo.findForinProgress(sub, pro,veri,assign,prio);
		System.out.println(pro);
		System.out.println(getData1);
			
		for(TaskDetail oneData:getData1)
		{	
			if(oneData.getStatusText().equals("Waiting for Someone Else") || oneData.getStatusText().equals("In Progress"))
			{
				todolist1.add(AllData(oneData));
				
			}
		}
		map.put("inProgressFilter",innermap);
		map.put("code", 200);
		map.put("status", true);
		map.put("message", "inProgress data");
		map.put("data", todolist1);
		return ResponseEntity.ok(map);
		 	}
		catch (java.lang.NullPointerException e) {
		 		List<TaskDetail> getData1=taskDetailRepo.findAll();
				 
				for(TaskDetail oneData:getData1)
				{	
//								todolist1.add(AllData(oneData));
								
								if(oneData.getStatusText().equals("Waiting for Someone Else") || oneData.getStatusText().equals("In Progress"))
								{
									todolist1.add(AllData(oneData));
								}
				
						
				}
				map.put("inProgressFilter",innermap);
				map.put("code", 200);
				map.put("status", true);
				map.put("message", "inProgress data");
				map.put("data", todolist1);
				return ResponseEntity.ok(map);
			}
	}
	
//	===========================================================================================================================================
	
	public ResponseEntity<?> completedFilter(taskfilter taskfilter){
		Map<String,Object> innermap = new LinkedHashMap<String , Object>();
		Map<String,Object> map = new LinkedHashMap<String , Object>();
		List<Map<String, Object>> todolist1=new ArrayList<Map<String, Object>>();

		try{
		String sub=taskfilter.completedFilter.getSubject();
		String pro=taskfilter.completedFilter.getProjectName();
		String veri=taskfilter.completedFilter.getVerifiedBy();
		String assign=taskfilter.completedFilter.getAssignedTo();
		String prio=taskfilter.completedFilter.getPriority();

		if(pro==null||pro.isEmpty())
			pro="";
		else
			innermap.put("projectName", pro);
		
		if(sub==null||sub.isEmpty())
			sub="";
		else
			innermap.put("subject", sub);
		
		if(veri==null||veri.isEmpty())
			veri="";
		else
			innermap.put("verifiedBy", veri);
		
		if(prio==null||prio.isEmpty())
			prio="";
		else
			innermap.put("priority", prio);
		
		if(assign==null||assign.isEmpty())
			assign="";
		else
			innermap.put("assignedTo", assign);
		
		List<TaskDetail> getData1=taskDetailRepo.findForcompleted(sub, pro,veri,assign,prio);
		System.out.println(pro);
		System.out.println(getData1);
			
		for(TaskDetail oneData:getData1)
		{	
			
				todolist1.add(AllData(oneData));
				
			
		}
		map.put("completedFilter",innermap);
		map.put("code", 200);
		map.put("status", true);
		map.put("message", "completedFilter data");
		map.put("data", todolist1);
		return ResponseEntity.ok(map);
		 	}
		catch (java.lang.NullPointerException e) {
		 		List<TaskDetail> getData1=taskDetailRepo.findAll();
				 
				for(TaskDetail oneData:getData1)
				{	
//								todolist1.add(AllData(oneData));
								
								if(oneData.getStatusText().contains("Completed"))
								{
									todolist1.add(AllData(oneData));
								}
				
						
				}
				map.put("completedFilter",innermap);
				map.put("code", 200);
				map.put("status", true);
				map.put("message", "completedFilter data");
				map.put("data", todolist1);
				return ResponseEntity.ok(map);
			}
	}
	
//	===========================================================================================================================================
	
	
	public ResponseEntity<?> ListAllTasks(taskfilter taskfilter){
		try {

			Map<String,Object> hashMap = new HashMap<String , Object>();

			Map<String,Object> innermap = new LinkedHashMap<String , Object>();
			Map<String,Object> map = new LinkedHashMap<String , Object>();
			
			List<Map<String, Object>> todolist2=new ArrayList<Map<String, Object>>();
			
			List<Map<String, Object>> todolist=new ArrayList<Map<String, Object>>();
			List<Map<String,Object>> inProgressList=new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> completedList=new ArrayList<Map<String,Object>>();

		
		String sub=taskfilter.globalFilter.getSubject();
		String pro=taskfilter.globalFilter.getProjectName();
		String veri=taskfilter.globalFilter.getVerifiedBy();
		String assign=taskfilter.globalFilter.getAssignedTo();
		String prio=taskfilter.globalFilter.getPriority();
		System.out.println(sub+pro);
		
		if(pro==null||pro.isEmpty())
			pro="";
		else
			innermap.put("projectName", pro);
		
		if(sub==null||sub.isEmpty())
			sub="";
		else
			innermap.put("subject", sub);
		
		if(veri==null||veri.isEmpty())
			veri="";
		else
			innermap.put("verifiedBy", veri);
		
		if(prio==null||prio.isEmpty())
			prio="";
		else
			innermap.put("priority", prio);
		
		if(assign==null||assign.isEmpty())
			assign="";
		else
			innermap.put("assignedTo", assign);
		getData=taskDetailRepo.findByForGlobal(sub, pro, veri, assign, prio);
		
		if(getData==null|| getData.isEmpty())
		{
			getData=taskDetailRepo.findAll();
		}	

		for(TaskDetail oneData:getData)
		{	

			if(oneData.getStatusText().equals("Not Started"))
			{
				todolist.add(AllData(oneData));
			}
			if(oneData.getStatusText().equals("Waiting for Someone Else") || oneData.getStatusText().equals("In Progress"))
			{
				inProgressList.add(AllData(oneData));
			}
			if(oneData.getStatusText().equals("Completed"))
			{
				completedList.add(AllData(oneData));
			}
		}

		hashMap.put("globalFilter",innermap);
		hashMap.put("todo", todolist);
		hashMap.put("inProgress", inProgressList);
		hashMap.put("Completed", completedList);
		map.put("code", 200);
		map.put("status", true);
		map.put("message", "All data");
		map.put("data", hashMap);

		return ResponseEntity.ok(map);
		}
		
		catch (NullPointerException e) {
			System.out.println("catch");
			List<TaskDetail> getData=taskDetailRepo.findAll();
			List<Map<String,Object>> todolist=new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> inProgressList=new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> completedList=new ArrayList<Map<String,Object>>();
			Map<String,Object> map = new LinkedHashMap<String , Object>();
			Map<String,Object> hashMap = new LinkedHashMap<String , Object>();

			for(TaskDetail oneData:getData)
			{	
				
				if(oneData.getStatusText().equals("Not Started"))
				{
					todolist.add(AllData(oneData));
				}
				if(oneData.getStatusText().equals("Waiting for Someone Else") || oneData.getStatusText().equals("In Progress"))
				{
					inProgressList.add(AllData(oneData));
				}
//				Completed
				if(oneData.getStatusText().equals("Completed"))
				{
					completedList.add(AllData(oneData));
				}
			}
			hashMap.put("globalFilter",new HashMap<String,Object>());
			hashMap.put("todo", todolist);
			hashMap.put("inProgress", inProgressList);
			hashMap.put("Completed", completedList);
			map.put("code", 200);
			map.put("status", true);
			map.put("message", "All data");
			map.put("data", hashMap);
			
			return ResponseEntity.ok(map);
		}
	}

	public Map<String,Object> AllData(TaskDetail oneData)
	{
//		Map<String,Object> hashMap = new LinkedHashMap<String , Object>();
		LinkedHashMap<String,Object> hashMap = new LinkedHashMap<String,Object>();
		hashMap.put("subject" , oneData.getSubject());
		hashMap.put("projectName" , oneData.getProjectName());
		hashMap.put("priority" , oneData.getPriority());
		hashMap.put("assignedTo" , oneData.getAssignedTo());
		hashMap.put("statusText" , oneData.getStatusText());
		hashMap.put("statusPercentage" , oneData.getStatusPercentage());
		hashMap.put("taskToken", oneData.getTaskToken());
		hashMap.put("startDate" , oneData.getStartDate());
		hashMap.put("dueDate" , oneData.getDueDate());
		hashMap.put("attachments", oneData.getAttachments()); 
		hashMap.put("verifiedBy" , oneData.getVerifiedBy());
		return hashMap;
	}
}