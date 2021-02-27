package com.JWTSptingWebApp.group.service;

import org.springframework.http.ResponseEntity;

import com.JWTSptingWebApp.group.dto.GroupUserDto;



public interface GroupService {
	
	public ResponseEntity<?> CreateorUpdate(GroupUserDto dto);
	public ResponseEntity<?> Listalldata();
	public ResponseEntity<?> DeleteByToken(String token);
	public ResponseEntity<?> UpdateByToken(String token,GroupUserDto dto);
	public ResponseEntity<?> findGroupByToken(String token);
	public ResponseEntity<?> userdetails();

	
	}
