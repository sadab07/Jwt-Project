package com.JWTSptingWebApp.springbootsecurity.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.JWTSptingWebApp.UserDetail.Repository.UserDetailRepository;
import com.JWTSptingWebApp.UserDetail.model.UserDetail;
import com.JWTSptingWebApp.logging.service.LoggingService;
import com.JWTSptingWebApp.springbootsecurity.config.CustomUserDetailsService;
import com.JWTSptingWebApp.springbootsecurity.config.JwtUtil;
import com.JWTSptingWebApp.springbootsecurity.model.AuthenticationRequest;
import com.JWTSptingWebApp.springbootsecurity.model.AuthenticationResponse;
import com.JWTSptingWebApp.springbootsecurity.model.UserDTO;


@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@CrossOrigin
public class AuthenticationController {

	static Logger log = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	public LoggingService loggingService;

	@Autowired
	public UserDetailRepository userDetailRepository;

	// @RequestMapping(value = "/authenticate", method = RequestMethod.POST)

	// @CrossOrigin(origins = "http://10.1.1.11:3004")
	@PostMapping(value = "/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		System.out.println("Call");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> userDetailMap = new HashMap<String, Object>();
		UserDetail userDetail = new UserDetail();
		try {

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (DisabledException e) {
			// throw new Exception("USER_DISABLED", e);
			map.put("code", 404);
			map.put("message", "USER_DISABLED");
			map.put("status", false);
			log.error("log ERROR {}", "USER_DISABLED");
			loggingService.createLog("DisabledException", new Timestamp(System.currentTimeMillis()), false, 404,
					e.getMessage());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
		} catch (BadCredentialsException e) {
			// throw new Exception("INVALID_CREDENTIALS", e);
			map.put("code", 400);
			map.put("message", "INVALID_CREDENTIALS");
			map.put("status", false);
			log.error("log ERROR {}", "INVALID_CREDENTIALS");
			loggingService.createLog("BadCredentialsException", new Timestamp(System.currentTimeMillis()), false, 400,
					e.getMessage());
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}

		UserDetails userdetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		String token = jwtUtil.generateToken(userdetails);

		userDetail = userDetailRepository.findByUsernameAndActive(authenticationRequest.getUsername(),true);
		
		map.put("code", 200);
		map.put("message", "Login Successfully");
		map.put("status", true);
		log.info("log INFO {}", "Login Successfully");
		loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "Login Successfully");
		userDetailMap.put("usertoken", userDetail.getTokenuser());
		userDetailMap.put("jwtToken", new AuthenticationResponse(token));
		map.put("data", userDetailMap);

		userDetail = userDetailRepository.findByUsernameAndActive(authenticationRequest.getUsername(),true);
		if (null == userDetail) {
			map.put("code", 404);
			map.put("message", "User Not Found.");
			map.put("status", false);
			map.put("username", authenticationRequest.getUsername());
			log.error("log ERROR {}", "User Not Found.");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 404, "User Not Found.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		} else {
			userDetail.setToken(token);
			userDetail.setLastlogin(new Timestamp(System.currentTimeMillis()));
			userDetail.setTokencreationdate(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
			userDetailRepository.save(userDetail);
			log.info("log INFO {}", "User Updated.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		String username = user.getUsername();
		String email = user.getEmail();

		if (null == email) {
			map.put("code", 404);
			map.put("message", "Email Address Required.");
			map.put("status", false);
			map.put("email", email);
			log.error("log ERROR {}", "Email Address Required.");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 404,
					"Email Address Required.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}

		if (null == username) {
			map.put("code", 404);
			map.put("message", "Username Required.");
			map.put("status", false);
			map.put("email", email);
			log.error("log ERROR {}", "Username Required.");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 404, "Username Required.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}

		UserDetail userdetails = userDetailRepository.findByEmailAndActive(email, true);

		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		boolean emailValid = email.matches(regex);

		if (emailValid == false) {
			map.put("code", 400);
			map.put("message", "Email Address Not Valid.");
			map.put("status", false);
			map.put("email", email);
			log.error("log ERROR {}", "Email Address Not Valid.");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400,
					"Email Address Not Valid.");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}

		if (null != userdetails) {
			map.put("code", 409);
			map.put("message", "Email Address Already Exist.");
			map.put("status", false);
			map.put("email", email);
			log.error("log ERROR {}", "Email Address Already Exist.");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 409,
					"Email Address Already Exist.");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
		}

		userdetails = userDetailRepository.findByUsernameAndActive(username, true);
		if (null != userdetails) {
			map.put("code", 409);
			map.put("message", "Username Already Exist.");
			map.put("status", false);
			map.put("username", username);
			log.error("log ERROR {}", "Username Already Exist.");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 409,
					"Username Already Exist.");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
		}

		map.put("code", 200);
		map.put("message", "Register Successfully");
		map.put("status", true);
		map.put("data", userDetailsService.save(user));
		log.info("log INFO {}", "Register Successfully");
		loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "Register Successfully");
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

}
