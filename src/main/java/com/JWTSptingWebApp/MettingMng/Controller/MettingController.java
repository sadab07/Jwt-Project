package com.JWTSptingWebApp.MettingMng.Controller;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.JWTSptingWebApp.MettingMng.Dto.MettingDto;
import com.JWTSptingWebApp.MettingMng.Model.Metting;
import com.JWTSptingWebApp.MettingMng.Service.MettingService;
import com.JWTSptingWebApp.MettingMng.repository.MettingRepository;
import com.JWTSptingWebApp.UserDetail.Dto.UserDetailDto;
import com.JWTSptingWebApp.UserDetail.Dto.UserDetailDto2;
import com.JWTSptingWebApp.UserDetail.Repository.UserDetailRepository;
import com.JWTSptingWebApp.UserDetail.Service.UserDetailMasterService;
import com.JWTSptingWebApp.UserDetail.ServiceImpl.UserDetailMasterServiceImpl;
import com.JWTSptingWebApp.UserDetail.model.UserDetail;
import com.JWTSptingWebApp.notes.dto.NotesDto;
import com.JWTSptingWebApp.notes.model.NotesDetail;
import com.JWTSptingWebApp.notes.repository.NotesRepo;
import com.JWTSptingWebApp.notes.service.NotesService;
import com.JWTSptingWebApp.singlefileupload.Dto.UploadFileResponse;
import com.JWTSptingWebApp.singlefileupload.model.DBFile;
import com.JWTSptingWebApp.singlefileupload.repository.DBFileRepository;
import com.JWTSptingWebApp.singlefileupload.service.DBFileStorageService;

@RestController
@RequestMapping("/Meeting")
public class MettingController {
	
	@Autowired
	private MettingService mettingService;
	@Autowired
	private UserDetailRepository userDetailRepository;
	@Autowired 
	UserDetailMasterServiceImpl userDetailMasterServiceImpl; 
	@Autowired
	NotesService notesService;
	@Autowired
	DBFileStorageService dBFileStorageService;
	@Autowired
	DBFileRepository dBFileRepository;
	@Autowired
	NotesRepo notesRepo;
	
	@PostMapping("/CreateMeeting")
	public ResponseEntity<?> createMeeting(@RequestParam("subject") String subject,@RequestParam("description") String description,
			@RequestParam("projectname") String projectname,@RequestParam("starttime")Timestamp startTime,@RequestParam("endtime")Timestamp endTime
			,@RequestParam("attachments") MultipartFile[] attachments,@RequestParam("attendees")String[] attendees,@RequestParam("notes") String[] notes)
		{
		Map<String, Object> map = new HashMap<String, Object>();
		String uuid = UUID.randomUUID().toString();
		String uid = uuid.toString().replaceFirst("[a-z]", uuid.toString().toUpperCase()).replace("-", "")
				.replaceFirst("[a-z]", uuid.toString().toLowerCase());
		String token = uid.substring(29, 37);
		
		if (subject.isEmpty()) {
			map.put("code", 404);
			map.put("message", "subject Required.");
			map.put("status", false);
			map.put("subject", subject);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		if (description.isEmpty()) {
			map.put("code", 404);
			map.put("message", "description Required.");
			map.put("status", false);
			map.put("description", description);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		if (projectname.isEmpty()) {
			map.put("code", 404);
			map.put("message", "projectname Required.");
			map.put("status", false);
			map.put("projectname", projectname);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		if (startTime.equals(null)) {
			map.put("code", 404);
			map.put("message", "startTime Required.");
			map.put("status", false);
			map.put("startTime", startTime);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		if (endTime.equals(null)) {
			map.put("code", 404);
			map.put("message", "endTime Required.");
			map.put("status", false);
			map.put("endTime", endTime);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		MettingDto mettingDto=new MettingDto(token,subject,description,projectname,startTime,endTime);
		
		Metting meeting=mettingService.convertDtoToModel(mettingDto);
		mettingService.createMeeting(meeting);
		NotesDetail note=new NotesDetail();
		
		for(int i=0;i<attendees.length;i++) {
			
			UserDetail userDetail= userDetailRepository.findByTokenuserAndActive(attendees[i],true);
			
			if(null==userDetail)
			{
					map.put("code", 404);
					map.put("message", "User not found");
					map.put("status", false);
					map.put("token", attendees[i]);
					return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
			}
			if (Arrays.asList(attachments).size() < 1)
			{
//				
			}
			else
			{
				for (int j = 0; j < attachments.length; j++) {
					
					if (attachments[j].getSize() < 0 || attachments[j].isEmpty()) 
					{
						//blank file doesn't store 
					}
					else {
						dBFileStorageService.storeFile(attachments[j], meeting);
					}
				}
			}
			
			if (Arrays.asList(notes).size() < 1)
			{
				
			}
			else
			{
				for(int k=0;k<notes.length;k++)
				{
					 note=notesService.createNotes(notes[k],meeting);	
				}
			}
		}
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		
		List<DBFile> meetingFile=dBFileRepository.findByMetting_token(token);
		String fileURL[]=new String[meetingFile.size()];
		for(int b=0;b<meetingFile.size();b++)
		{
			fileURL[b]=meetingFile.get(b).getDownloadUrl();
		}
		map1.put("Meeting", meeting);
		map1.put("attendees",attendees );
		map1.put("attachments", fileURL);
		map1.put("notes", notes);
		
		map.put("code", "200");
		map.put("Data", map1);
		map.put("message", "Meeting created successfully");
		map.put("status", "true");
		return ResponseEntity.ok(map);
	}
}