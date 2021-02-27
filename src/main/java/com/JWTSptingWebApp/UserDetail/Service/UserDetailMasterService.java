package com.JWTSptingWebApp.UserDetail.Service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.JWTSptingWebApp.UserDetail.Dto.UserDetailDto;
import com.JWTSptingWebApp.UserDetail.Dto.UserDetailDto2;
import com.JWTSptingWebApp.UserDetail.Dto.UserDetailDto3;

public interface UserDetailMasterService {
	
	public void createUserDetail(UserDetailDto userDetailDto);
	
	public List<UserDetailDto2> getAllUserDetail();
	
	public void deleteUserDetail(String token);
	
	public ResponseEntity<?> updateUserDetail(UserDetailDto user);

	public List<UserDetailDto3> findAllUserByTokenuser(String token);
	
	public void sendEmail(String recipient,String firstname,String lastname,Timestamp createdTime);

	
	
	}
