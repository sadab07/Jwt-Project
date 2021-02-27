package com.JWTSptingWebApp.jeniluserdetail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.JWTSptingWebApp.jeniluserdetail.dto.JenilUserDetailDto;
import com.JWTSptingWebApp.jeniluserdetail.service.JenilUserDetailService;

@RestController
@RequestMapping("/jeniluser")
public class JenilUserDetailController {

	@Autowired
	JenilUserDetailService jenilUserSer;
	
	@PostMapping("/create")
	public ResponseEntity<?> createUser(@RequestBody JenilUserDetailDto jenilUserDto){
	
		return jenilUserSer.createUser(jenilUserDto); 
	}
	
	@PostMapping("/get")
	public ResponseEntity<?> getUsers(boolean active){
		return jenilUserSer.getUsers(active);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> deleteUser(@RequestParam String usertoken){
		return jenilUserSer.deleteUser(usertoken);
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> updateUser(@RequestParam String usertoken , @RequestBody JenilUserDetailDto jenilUserDto){
		return jenilUserSer.updateUser(usertoken , jenilUserDto);
	}
	
}
