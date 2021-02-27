package com.JWTSptingWebApp.group.controller;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JWTSptingWebApp.group.dto.GroupUserDto;
import com.JWTSptingWebApp.group.service.GroupService;

@RestController
@RequestMapping("/group")
public class GroupController {
	@Autowired
	GroupService service;
		
	
	@PostMapping("/add")
	public ResponseEntity<?> Insertdata(@RequestBody GroupUserDto dto){
//		try {
			return service.CreateorUpdate(dto);

	}
	
	
	
	@GetMapping("/getAll")
	public ResponseEntity<?> ListAllData(){
		
		return service.Listalldata();
	}
	
	@GetMapping("/findByToken/{token}")
	public ResponseEntity<?> findGroupByToken(@PathVariable String token){
		
		return service.findGroupByToken(token);
	}
	
	@DeleteMapping("/delete/{token}")
	public ResponseEntity<?> Delete(@PathVariable String token ){
		
		return service.DeleteByToken(token);
		
	}
	
	@PutMapping("/update/{token}")
	public ResponseEntity<?> Update(@PathVariable String token,@RequestBody GroupUserDto dto ){
		
		return service.UpdateByToken(token, dto);
		
	}
	@GetMapping("/getUsers")
	public ResponseEntity<?> getAlluserdetails(){
//		return null;
		return service.userdetails();
	}
//userdetails
}
