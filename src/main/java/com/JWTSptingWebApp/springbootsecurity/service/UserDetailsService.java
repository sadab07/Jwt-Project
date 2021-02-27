package com.JWTSptingWebApp.springbootsecurity.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.JWTSptingWebApp.springbootsecurity.model.UserDTO;

@RestController
public interface UserDetailsService {

	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestParam String email,HttpServletRequest request);
	
	@PostMapping("/verify-otp")
	public ResponseEntity<?> verifyOtp(@RequestParam String otp);
	
	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	public ResponseEntity<?> changePassword(@RequestBody UserDTO user);
}
