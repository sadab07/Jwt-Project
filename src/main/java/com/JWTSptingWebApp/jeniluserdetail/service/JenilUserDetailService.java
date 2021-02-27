package com.JWTSptingWebApp.jeniluserdetail.service;

import org.springframework.http.ResponseEntity;

import com.JWTSptingWebApp.jeniluserdetail.dto.JenilUserDetailDto;

public interface JenilUserDetailService {

	ResponseEntity<?> createUser(JenilUserDetailDto jenilUserDto);

	ResponseEntity<?> getUsers(boolean active);

	ResponseEntity<?> deleteUser(String usertoken);

	ResponseEntity<?> updateUser(String usertoken, JenilUserDetailDto jenilUserDto);



}
