package com.JWTSptingWebApp.UserDetail.Controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import com.JWTSptingWebApp.UserDetail.Dto.UserDetailDto;
import com.JWTSptingWebApp.UserDetail.Dto.UserDetailDto2;
import com.JWTSptingWebApp.UserDetail.Repository.UserDetailRepository;
import com.JWTSptingWebApp.UserDetail.Service.UserDetailMasterService;
import com.JWTSptingWebApp.UserDetail.ServiceImpl.UserDetailMasterServiceImpl;
import com.JWTSptingWebApp.UserDetail.encrupt.AESEncryptionDecryption;
import com.JWTSptingWebApp.UserDetail.model.UserDetail;
import com.JWTSptingWebApp.logging.repository.LoggingRepository;
import com.JWTSptingWebApp.logging.service.LoggingService;
import com.JWTSptingWebApp.springbootsecurity.config.SpringSecurityConfiguration;


@RestController
@RequestMapping(value = "/user")
public class UserDetailController {
	static Logger log = LoggerFactory.getLogger(UserDetailController.class);
	@Autowired
	private UserDetailMasterService userDetailMasterService;
	@Autowired
	public SpringSecurityConfiguration springSecurityConfiguration;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	@Autowired
	private AESEncryptionDecryption aESEncryptionDecryption;
	@Autowired
	private UserDetailRepository userDetailRepository;
	@Autowired
	UserDetailMasterServiceImpl UserDetailserviceImpl;
	@Autowired
	public LoggingService loggingService;
	@Autowired
	public LoggingRepository loggingRepository;
	
	@GetMapping(value = "/listAll")
	public ResponseEntity<?> getAllUserDetail() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("message", "Got all user successfully");
		map.put("code", 200);
		map.put("status", true);
		map.put("data", userDetailMasterService.getAllUserDetail());
		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/findByToken/{token}")
	public ResponseEntity<?> getAllUserByToken(@PathVariable("token") String token) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserDetail userdetail = userDetailRepository.findByTokenuserAndActive(token, true);
		if (null == userdetail) {
			map.put("code", 404);
			map.put("message", "User not found");
			map.put("status", false);
			map.put("token", token);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		} else {
			map.put("message", "Found By token User successfully");
			map.put("code", 200);
			map.put("status", true);
			map.put("data", userDetailMasterService.findAllUserByTokenuser(token));

			return ResponseEntity.ok(map);
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> createUserDetail(@RequestBody UserDetailDto user) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		String firstname = user.getFirstName();
		String lastname = user.getLastName();
		String username = user.getUsername();
		String email = user.getEmail();
		String contact = user.getContact();
		String designation = user.getDesignation();
		String bloodgroup = user.getBloodGroup();
		String gender = user.getGender();
		String address = user.getAddress();
		String password = user.getPassword();
		String confirmpassword = user.getConfirmPassword();
	
//		final String secretKey = "secrete";
//		String encryptedPassword=aESEncryptionDecryption.encrypt(password, secretKey);
		
		
		UUID uuid = UUID.randomUUID();
		String uid = uuid.toString().replaceFirst("[a-z]", uuid.toString().toUpperCase()).replace("-", "")
				.replaceFirst("[a-z]", uuid.toString().toLowerCase());
		String tokenuser = uid.substring(29, 37);
		user.setTokenuser(tokenuser);
		Timestamp createdTime = new Timestamp(System.currentTimeMillis());
		user.setCreatedTime(createdTime);
		user.setUpdateTime(createdTime);
		
		if (firstname.isEmpty()) {
			map.put("code", 404);
			map.put("message", "First Name Required.");
			map.put("status", false);
			map.put("firstname", firstname);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		if (lastname.isEmpty()) {
			map.put("code", 404);
			map.put("message", "Last Name Required.");
			map.put("status", false);
			map.put("lastname", lastname);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		if (username.isEmpty()) {
			map.put("code", 404);
			map.put("message", "Username Required.");
			map.put("status", false);
			map.put("username", username);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		if (null == email) {
			map.put("code", 404);
			map.put("message", "Email Address Required.");
			map.put("status", false);
			map.put("email", email);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		if (contact.isEmpty()) {
			map.put("code", 404);
			map.put("message", "Contact number Required.");
			map.put("status", false);
			map.put("contact", contact);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		String regexcontact =  "^[0-9]\\d{9}$";
		boolean contactvalid = contact.matches(regexcontact);
		if (contactvalid == false||contact.length() > 10 || contact.length() < 10) {
			map.put("code", 400);
			map.put("message", "Contact number Not Valid.");
			map.put("status", false);
			map.put("contact", contact);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		if (designation.isEmpty()) {
			map.put("code", 404);
			map.put("message", "designation Required.");
			map.put("status", false);
			map.put("designation", designation);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		if (bloodgroup.isEmpty()) {
			map.put("code", 404);
			map.put("message", "bloodgroup Required.");
			map.put("status", false);
			map.put("bloodgroup", bloodgroup);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		if (gender.isEmpty()) {
			map.put("code", 404);
			map.put("message", "gender Required.");
			map.put("status", false);
			map.put("gender", gender);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		if (address.isEmpty()) {
			map.put("code", 404);
			map.put("message", "address Required.");
			map.put("status", false);
			map.put("address", address);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
//		if (role.isEmpty() || !(role.matches("ROLE_USER") || role.matches("ROLE_ADMIN"))) {
//			map.put("code", 404);
//			map.put("message", "Enter Required Role(ROLE_USER / ROLE_ADMIN)");
//			map.put("status", false);
//			map.put("role", role);
//			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
//		}
		if (password.isEmpty()) {
			map.put("code", 404);
			map.put("message", "password Required.");
			map.put("status", false);
			map.put("password", password);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		if (confirmpassword.isEmpty()) {
			map.put("code", 404);
			map.put("message", "confirmpassword Required.");
			map.put("status", false);
			map.put("password", confirmpassword);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		UserDetail userdetail = userDetailRepository.findByEmailAndActive(email, true);

		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		boolean emailValid = email.matches(regex);

		if (emailValid == false) {
			map.put("code", 400);
			map.put("message", "Email Address Not Valid.");
			map.put("status", false);
			map.put("email", email);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}

		if (null != userdetail) {
			map.put("code", 409);
			map.put("message", "Email Address Already Exist.");
			map.put("status", false);
			map.put("email", email);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		userdetail = userDetailRepository.findByUsernameAndActive(username, true);
		if (null != userdetail) {
			map.put("code", 409);
			map.put("message", "Username Already Exist.");
			map.put("status", false);
			map.put("username", username);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}

		if (!(password.equals(confirmpassword))) {
			map.put("code", 410);
			map.put("message", "Password and confirm password are not same");
			map.put("status", false);
			map.put("password", password);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
	user.setPassword(bcryptEncoder.encode(user.getPassword()));
//		user.setPassword(encryptedPassword);
		UserDetailserviceImpl.sendEmail(email,firstname,lastname,createdTime);
		map.put("code", 200);
		//map.put("message", "Registered Successfully");
		//
	
		
		loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "Register Successfully");
		log.info("log INFO {}", "Register Successfully");
		
		map.put("status", true);
		userDetailMasterService.createUserDetail(user);
		  UserDetailDto2 userdetailDto2=UserDetailserviceImpl.convertDTOToDTO1(user);
		map.put("data", userdetailDto2);
		return ResponseEntity.ok(map);
	}

	@DeleteMapping("/delete/{tokenUser}")
	public ResponseEntity<?> delete(@PathVariable String tokenUser, UserDetailDto user) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserDetail userdetail = userDetailRepository.findByTokenuserAndActive(tokenUser, true);
		if (null == userdetail) {
			map.put("code", 404);
			map.put("message", "User not found");
			map.put("status", false);
			map.put("token", tokenUser);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		} else {
			
			userDetailMasterService.deleteUserDetail(tokenUser);
			map.put("code", 200);
			map.put("Data", tokenUser);
			map.put("message", "Delete Successfully");
			map.put("status", true);
			return ResponseEntity.ok(map);
		}
	}

	@PutMapping("/update/{token}")
	public ResponseEntity<?> updateUser(@PathVariable("token") String token, @RequestBody UserDetailDto user) {
		Map<String, Object> map = new HashMap<String, Object>();

		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String username = user.getUsername();
		String email = user.getEmail();
		String contact = user.getContact();
		String designation = user.getDesignation();
		String bloodGroup = user.getBloodGroup();
		String gender = user.getGender();
		String address = user.getAddress();
		String password = user.getPassword();
		String confirmPassword = user.getConfirmPassword();
		
		/*
		 * final String secretKey = "secrete"; Stri
		ng encryptedPassword=aESEncryptionDecryption.encrypt(password, secretKey); */
		
		Timestamp createtime;
		String email1;
		String username1;
		
		UserDetail userdetail = userDetailRepository.findByTokenuserAndActive(token, true);
		if (userDetailRepository.existsByTokenuserAndActive(token, true)) {
			Long id = userdetail.getUserid();
			user.setToken(token);
			user.setTokenuser(userdetail.getTokenuser());
			user.setUserid(id);
			
			createtime=userdetail.getCreatedTime();
			user.setCreatedTime(createtime);
			
			email1 = userdetail.getEmail();
			username1 = userdetail.getUsername();
			if (firstName.isEmpty()) {
				map.put("code", 404);
				map.put("message", "First Name Required.");
				map.put("status", false);
				map.put("firstName", firstName);
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
			}
			if (lastName.isEmpty()) {
				map.put("code", 404);
				map.put("message", "Last Name Required.");
				map.put("status", false);
				map.put("lastName", lastName);
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
			}
			if (username.isEmpty()) {
				map.put("code", 404);
				map.put("message", "Username Required.");
				map.put("status", false);
				map.put("username", username);
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
			}
			if (null == email) {
				map.put("code", 404);
				map.put("message", "Email Address Required.");
				map.put("status", false);
				map.put("email", email);
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
			}
			if (contact.isEmpty()) {
				map.put("code", 404);
				map.put("message", "Contact number Required.");
				map.put("status", false);
				map.put("contact", contact);
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
			}
			//String regexcontact = "(0/91)?[7-9][0-9]{9}";
			String regexcontact =  "^[0-9]\\d{9}$";
			boolean contactvalid = contact.matches(regexcontact);
			if (contactvalid == false) {
				map.put("code", 400);
				map.put("message", "Contact number Not Valid.");
				map.put("status", false);
				map.put("contact", contact);
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
			}
			if (designation.isEmpty()) {
				map.put("code", 404);
				map.put("message", "designation Required.");
				map.put("status", false);
				map.put("designation", designation);
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
			}
			if (bloodGroup.isEmpty()) {
				map.put("code", 404);
				map.put("message", "bloodgroup Required.");
				map.put("status", false);
				map.put("bloodGroup", bloodGroup);
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
			}
			if (gender.isEmpty()) {
				map.put("code", 404);
				map.put("message", "gender Required.");
				map.put("status", false);
				map.put("gender", gender);
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
			}
			if (address.isEmpty()) {
				map.put("code", 404);
				map.put("message", "address Required.");
				map.put("status", false);
				map.put("address", address);
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
			}
			if (password.isEmpty()) {
				map.put("code", 404);
				map.put("message", "password Required.");
				map.put("status", false);
				map.put("password", password);
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
			}
			if (confirmPassword.isEmpty()) {
				map.put("code", 404);
				map.put("message", "confirmpassword Required.");
				map.put("status", false);
				map.put("confirmPassword", confirmPassword);
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
			}
			String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			boolean emailValid = email.matches(regex);
			if (emailValid == false) {
				map.put("code", 400);
				map.put("message", "Email Address Not Valid.");
				map.put("status", false);
				map.put("email", email);
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
			}
			if (!(password.equals(confirmPassword))) {
				map.put("code", 410);
				map.put("message", "Password and confirm password are not same");
				map.put("status", false);
				map.put("password", password);
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
			}

			userdetail = userDetailRepository.findByEmailAndActive(email, true);
			UserDetail u1 = userDetailRepository.findByUsernameAndActive(username, true);
			if (null != userdetail || null != u1) {
				if (!(email1.equals(email) || username1.equals(username))) {
					map.put("code", 404);
					map.put("message", "Email id or Username already exist");
					map.put("status", false);
					map.put("email", email);
					map.put("username", username);
					return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
				}
			}
			Timestamp updateTime = new Timestamp(System.currentTimeMillis());
			user.setPassword(bcryptEncoder.encode(user.getPassword()));
		//	user.setPassword(encryptedPassword);
			user.setUpdateTime(updateTime);
		    UserDetailDto2 userdetailDto2=UserDetailserviceImpl.convertDTOToDTO1(user);
				
			map.put("code", 200);
			map.put("data", userdetailDto2);
			map.put("message", "Updated Successfully");
			map.put("status", true);
			userDetailMasterService.updateUserDetail(user);
			return ResponseEntity.ok(map);
		} else {
			map.put("code", 100);
			map.put("message", "User not found");
			map.put("status", false);
			map.put("token", token);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
	}
}
