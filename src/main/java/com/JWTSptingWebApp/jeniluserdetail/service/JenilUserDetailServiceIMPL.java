package com.JWTSptingWebApp.jeniluserdetail.service;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import javax.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.JWTSptingWebApp.jenilblogmng.model.JenilBlog;
import com.JWTSptingWebApp.jenilblogmng.model.JenilCoverImage;
import com.JWTSptingWebApp.jenilblogmng.repository.JenilBlogRepository;
import com.JWTSptingWebApp.jenilblogmng.repository.JenilCoverImageRepository;
import com.JWTSptingWebApp.jenilsinglefile.dto.JenilFileDetailDto;
import com.JWTSptingWebApp.jenilsinglefile.model.JenilFileDetail;
import com.JWTSptingWebApp.jenilsinglefile.repository.JenilFileRepository;
import com.JWTSptingWebApp.jenilsinglefile.service.JenilFileIMPLService;
import com.JWTSptingWebApp.jeniluserdetail.dto.JenilUserDetailDto;
import com.JWTSptingWebApp.jeniluserdetail.model.JenilUserDetail;
import com.JWTSptingWebApp.jeniluserdetail.model.Response;
import com.JWTSptingWebApp.jeniluserdetail.repository.JenilUserDetailRepository;
import com.JWTSptingWebApp.logging.service.LoggingService;


@Service
public class JenilUserDetailServiceIMPL implements JenilUserDetailService{

	@Autowired
	JenilUserDetailRepository jenilUserRepo;
	
	@Autowired
	PasswordEncoder bcryptEncoder;
	
	@Autowired
	private JavaMailSender javamail;
	
	@Autowired
	JenilBlogRepository blogRepo;
	
	@Autowired
	JenilCoverImageRepository imagerepo;
	
	@Autowired
	JenilFileRepository filerepo;
	
	@Autowired
	LoggingService loggingService;
	
	Logger logger = LoggerFactory.getLogger(JenilFileIMPLService.class);
	
	Response response = new Response();
	ModelMapper modelMapper = new ModelMapper();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime ldt = LocalDateTime.now();
	
	
	
	void sendEmail(String email , String username ) throws MessagingException, UnsupportedEncodingException {
				
		SimpleMailMessage msg = new SimpleMailMessage();
		
		msg.setTo(email);
        msg.setSubject("Management Tool Welcomes");
        msg.setText("You are Sign up with USERNAME"+" : "+ username);
      
        javamail.send(msg);
        
	}
	
	private static String generateRandom() {
		String aToZ="01234ABCDEFGHIJKLMNO012345PQRSTUVWXYZ678901235abcdefghijklmnopqrstuvwxyz6789";
	    
		Random rand=new Random();
	    StringBuilder res=new StringBuilder();
	    
	    for (int i = 0; i < 8; i++) {
	    	int randIndex=rand.nextInt(aToZ.length());
	    	res.append(aToZ.charAt(randIndex));
	        }
		return	res.toString();
	}
	
	
	
	
	@Override
	public ResponseEntity<?> createUser(JenilUserDetailDto jenilUserDto) {
		
		LinkedHashMap<String ,Object> hashmap = new LinkedHashMap<String, Object>();
		
		String regexp = "^[a-zA-Z]*$";
		String firstName = jenilUserDto.getFirstname();
		if(null==firstName ){
			hashmap.put("code", 400);
			hashmap.put("message", "First Name is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","First Name is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "First Name is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(firstName ==""){
			hashmap.put("code", 400);
			hashmap.put("message", "First Name is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","First Name is not Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "First Name is not Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		boolean firstNameValid = firstName.matches(regexp);
		
		if(!(firstNameValid)){
			hashmap.put("code", 404);
			hashmap.put("message", "First Name has Only Alphabets.");
			hashmap.put("status", false);
			hashmap.put("firstName", firstName);
			logger.error("log ERROR {}","First Name has Only Alphabets");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "First Name has Only Alphabets");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(hashmap);
		}	
////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		String lastName = jenilUserDto.getLastname();
		if(null==lastName ){
			hashmap.put("code", 404);
			hashmap.put("message", "Last Name is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Last Name is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Last Name is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(lastName ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "Last Name is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Last Name is not Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Last Name is not Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		boolean lastNameValid = lastName.matches(regexp);
		
		if(!(lastNameValid)){
			hashmap.put("code", 404);
			hashmap.put("message", "Last Name has Only Alphabets.");
			hashmap.put("status", false);
			hashmap.put("firstName", firstName);
			logger.error("log ERROR {}","Last Name has Only Alphabets");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Last Name has Only Alphabets");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(hashmap);
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		String emailRegex ="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		
		String email = jenilUserDto.getEmail();
		if(null==email ){
			hashmap.put("code", 404);
			hashmap.put("message", "Email is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Email is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Email is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(email ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "Email is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Email is Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Email is Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		
		boolean emailValid = email.matches(emailRegex);
		
		if(!(emailValid)) {
			hashmap.put("code", 404);
			hashmap.put("message", "Email is not Valid.");
			hashmap.put("status", false);
			hashmap.put("email", email);
			logger.error("log ERROR {}","Email is not Valid");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Email is not Valid");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(hashmap);
		}
		
		JenilUserDetail detail = jenilUserRepo.findByEmail(email);
		
		if(null != detail) {
			hashmap.put("code", 404);
			hashmap.put("message", "Email is Already Exist.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Email is Already Exist");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Email is Already Exist");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(hashmap);
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		
		String username = jenilUserDto.getUsername();
		if(null==username ){
			hashmap.put("code", 404);
			hashmap.put("message", "UserName is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","UserName is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "UserName is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(username ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "UserName is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","UserName is Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "UserName is Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		
		JenilUserDetail repoUname = jenilUserRepo.findByUsername(username);
		
		if(null != repoUname) {
			hashmap.put("code", 404);
			hashmap.put("message", "UserName is Already Exist.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","UserName is Already Exist");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "UserName is Already Exist");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(hashmap);
		}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		
		String contact = jenilUserDto.getContact();
		String cregex = "^[0-9]*$";
		
		if(null==contact ){
			hashmap.put("code", 404);
			hashmap.put("message", "Contact is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Contact is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Contact is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(contact ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "Contact is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Contact is Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Contact is Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		
		boolean contactValid = contact.matches(cregex);
		
		if(!(contactValid)) {
			hashmap.put("code", 404);
			hashmap.put("message", "Contact is not Valid.");
			hashmap.put("status", false);
			hashmap.put("contact", contact);
			logger.error("log ERROR {}","Contact is not Valid");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Contact is not Valid");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(hashmap);
		}
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		
		
		String password = jenilUserDto.getPassword();
		String confirmpassword = jenilUserDto.getConfirmpassword();
		
		if(password ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "Password is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Password is Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Password is Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(null==password ){
			hashmap.put("code", 404);
			hashmap.put("message", "Password is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Password is not Available.");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Password is not Available.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(null==confirmpassword ){
			hashmap.put("code", 404);
			hashmap.put("message", "ConfirmPassword is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","ConfirmPassword is not Available.");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "ConfirmPassword is not Available.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(confirmpassword ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "ConfirmPassword is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","ConfirmPassword is Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "ConfirmPassword is Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		
		boolean validpass = password.equals(confirmpassword);
		if (validpass==false) {
			hashmap.put("code", 410);
			hashmap.put("message", "Password and confirm password are not same");
			hashmap.put("status", false);
			hashmap.put("password", password);
			logger.error("log ERROR {}","Password and confirm password are not same");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Password and confirm password are not same");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(hashmap);
		}
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		
		String designation = jenilUserDto.getDesignation();
		if(null==designation ){
			hashmap.put("code", 404);
			hashmap.put("message", "Designation is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Designation is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Designation is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(designation ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "Designation is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Designation is Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Designation is Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		String bg = jenilUserDto.getBloodgroup();
		if(null==bg ){
			hashmap.put("code", 404);
			hashmap.put("message", "Bloodgroup is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Bloodgroup is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Bloodgroup is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(bg ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "Bloodgroup is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Bloodgroup is Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Bloodgroup is Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		String gender = jenilUserDto.getGender();
		if(null==gender ){
			hashmap.put("code", 404);
			hashmap.put("message", "Gender is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Gender is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Gender is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(gender ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "Gender is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Gender is Empty.");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Gender is Empty.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		boolean male = gender.equals("Male");
		boolean female = gender.equals("Female");
		if(!(male || female)) {
			hashmap.put("code", 404);
			hashmap.put("message", "Gender has only Male or Female.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Gender has only Male or Female");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Gender has only Male or Female");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		String address = jenilUserDto.getAddress();
		if(null==address ){
			hashmap.put("code", 404);
			hashmap.put("message", "Address is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Address is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Address is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(address ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "Address is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Address is Empty.");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Address is Empty.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		String role = jenilUserDto.getRole();
		if(null==role ){
			hashmap.put("code", 404);
			hashmap.put("message", "Role is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Role is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Role is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(role ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "Role is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Role is Empty..");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Role is Empty..");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		
		boolean uservalid = role.equals("ROLE_USER");
		boolean adminvalid = role.equals("ROLE_ADMIN");
		if(!(uservalid || adminvalid)) {
			hashmap.put("code", 404);
			hashmap.put("message", "Role has only ROLE_USER or ROLE_ADMIN.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Role has only ROLE_USER or ROLE_ADMIN.");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Role has only ROLE_USER or ROLE_ADMIN.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
/////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		else 
		{
			jenilUserDto.setUsertoken(generateRandom());
			
			
			jenilUserDto.setCreatedtime(dtf.format(ldt));

			jenilUserDto.setPassword(bcryptEncoder.encode(jenilUserDto.getPassword()));
			jenilUserDto.setActive(true);
			
			JenilUserDetail jenilUser = modelMapper.map(jenilUserDto, JenilUserDetail.class);
			jenilUserRepo.save(jenilUser);
		
			LinkedHashMap<String, Object> map = new LinkedHashMap<String , Object>();
			map.put("firstName", jenilUser.getFirstname());
			map.put("lastName", jenilUser.getLastname());
			map.put("email", jenilUser.getEmail());
			map.put("userName", jenilUser.getUsername());
			map.put("contact", jenilUser.getContact());
			map.put("designation", jenilUser.getDesignation());				
			map.put("createdTime", jenilUser.getCreatedtime());				
			map.put("bloodgroup", jenilUser.getBloodgroup());
			map.put("gender", jenilUser.getGender());
			map.put("address", jenilUser.getAddress());
			map.put("password", jenilUser.getPassword());
			map.put("role", jenilUser.getRole());
	
			try {
				sendEmail(email, username);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("log Info {}","User has created");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "User has created");
		return ResponseEntity.ok(response.addMap(map));
		
	}
}
	

	@Override
	public ResponseEntity<?> getUsers(boolean active) {
		List<JenilUserDetail> listJenilUser = jenilUserRepo.findByActive(active);
		logger.info("log Info {}","AllUsers get successfully");
		loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "AllUsers get successfully");
		return ResponseEntity.ok(response.getUseraddMap(listJenilUser));
	}
	@Override
	public ResponseEntity<?> deleteUser(String usertoken) {
		JenilUserDetail jenilUser = jenilUserRepo.findByUsertoken(usertoken);
		LinkedHashMap<String, Object> hashmap = new LinkedHashMap<String , Object>();
		if(null==jenilUser) {
			hashmap.put("code", 404);
			hashmap.put("message", "User is not Found.");
			hashmap.put("status", false);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		jenilUser.setActive(false);
		jenilUserRepo.save(jenilUser);
		
		JenilBlog blog = blogRepo.findByAuthortoken(usertoken);
		blog.setActive(false);
		
		String blogtoken = blog.getBlogtoken();
		
		JenilCoverImage image = imagerepo.findByBlogtoken(blogtoken);
		image.setActive(false);
		
		List<JenilFileDetail> file = filerepo.findByBlogtoken(blogtoken);
		for(JenilFileDetail f: file) {
			f.setActive(false);
		}
		
		blogRepo.save(blog);
		logger.info("log Info {}","User is deleted succesfully, if user has create blog, upload file and cover image then it's aslo deleted succesfully");
		loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "User is deleted succesfully, if user has create blog, upload file and cover image then it's aslo deleted succesfully");
		return ResponseEntity.ok(response.deleteUseraddMap(usertoken));
	}
	
	
	
	@Override
	public ResponseEntity<?> updateUser(String usertoken, JenilUserDetailDto jenilUserDto) {
		JenilUserDetail jenilUser = jenilUserRepo.findByUsertoken(usertoken);
		LinkedHashMap<String, Object> hashmap = new LinkedHashMap<String , Object>();
		boolean active = jenilUser.getActive();
		if(active==false) {
			hashmap.put("code", 404);
			hashmap.put("message", "User has been Deleted");
			hashmap.put("status", false);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
//////////////////////////////////////////////////////////////////////////////////////////////////////////		
		String regexp = "^[a-zA-Z]*$";
		String firstName = jenilUserDto.getFirstname();
		if(null==firstName ){
			hashmap.put("code", 400);
			hashmap.put("message", "First Name is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","First Name is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "First Name is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(firstName ==""){
			hashmap.put("code", 400);
			hashmap.put("message", "First Name is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","First Name is not Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "First Name is not Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		boolean firstNameValid = firstName.matches(regexp);
		
		if(!(firstNameValid)){
			hashmap.put("code", 404);
			hashmap.put("message", "First Name has Only Alphabets.");
			hashmap.put("status", false);
			hashmap.put("firstName", firstName);
			logger.error("log ERROR {}","First Name has Only Alphabets");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "First Name has Only Alphabets");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(hashmap);
		}
//////////////////////////////////////////////////////////////////////////////////////////////////////////		
		String lastName = jenilUserDto.getLastname();
		if(null==lastName ){
			hashmap.put("code", 400);
			hashmap.put("message", "Last Name is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Last Name is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Last Name is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(lastName ==""){
			hashmap.put("code", 400);
			hashmap.put("message", "Last Name is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Last Name is not Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Last Name is not Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		
		boolean lastNameValid = lastName.matches(regexp);
		
		if(!(lastNameValid)){
			hashmap.put("code", 404);
			hashmap.put("message", "Last Name has Only Alphabets.");
			hashmap.put("status", false);
			hashmap.put("firstName", firstName);
			logger.error("log ERROR {}","Last Name has Only Alphabets");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Last Name has Only Alphabets");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(hashmap);
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		String emailRegex ="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		
		String email = jenilUserDto.getEmail();
		if(null==email ){
			hashmap.put("code", 404);
			hashmap.put("message", "Email is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Email is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Email is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(email ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "Email is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Email is not Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Email is not Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		boolean emailValid = email.matches(emailRegex);
		
		if(!(emailValid)) {
			hashmap.put("code", 404);
			hashmap.put("message", "Email is not Valid.");
			hashmap.put("status", false);
			hashmap.put("email", email);
			logger.error("log ERROR {}","Email is not Valid");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Email is not Valid");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(hashmap);
		}
		
		JenilUserDetail detail = jenilUserRepo.findByEmail(email);
		
		if(null != detail) {
			hashmap.put("code", 404);
			hashmap.put("message", "Email is Already Exist.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Email is Already Exist");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Email is Already Exist");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(hashmap);
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		String username = jenilUserDto.getUsername();
		if(null==username ){
			hashmap.put("code", 404);
			hashmap.put("message", "UserName is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","UserName is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "UserName is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(username ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "UserName is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","UserName is not Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "UserName is not Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		
		JenilUserDetail repoUname = jenilUserRepo.findByUsername(username);
		
		if(null != repoUname) {
			hashmap.put("code", 404);
			hashmap.put("message", "UserName is Already Exist.");
			hashmap.put("status", false);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(hashmap);
		}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		String contact = jenilUserDto.getContact();
		String cregex = "^[0-9]*$";
		
		if(null==contact ){
			hashmap.put("code", 404);
			hashmap.put("message", "Contact is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Contact is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Contact is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(contact ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "Contact is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Contact is not Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Contact is not Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		
		boolean contactValid = contact.matches(cregex);
		
		if(!(contactValid)) {
			hashmap.put("code", 404);
			hashmap.put("message", "Contact has Only Number.");
			hashmap.put("status", false);
			hashmap.put("contact", contact);
			logger.error("log ERROR {}","Contact is not Valid");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Contact is not Valid");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(hashmap);
		}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		String password = jenilUserDto.getPassword();
		String confirmpassword = jenilUserDto.getConfirmpassword();
		
		if(password ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "Password is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Password is not Empty.");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Password is not Empty.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(null==password ){
			hashmap.put("code", 404);
			hashmap.put("message", "Password is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Password is not Available.");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Password is not Available.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(null==confirmpassword ){
			hashmap.put("code", 404);
			hashmap.put("message", "ConfirmPassword is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","ConfirmPassword is not Available.");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "ConfirmPassword is not Available.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(confirmpassword ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "ConfirmPassword is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","ConfirmPassword is not Empty.");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "ConfirmPassword is not Empty.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		
		boolean validpass = password.equals(confirmpassword);
		if (validpass==false) {
			hashmap.put("code", 410);
			hashmap.put("message", "Password and confirm password are not same");
			hashmap.put("status", false);
			hashmap.put("password", password);
			logger.error("log ERROR {}","Password and confirm password are not same");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Password and confirm password are not same");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(hashmap);
		}
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		String designation = jenilUserDto.getDesignation();
		if(null==designation ){
			hashmap.put("code", 404);
			hashmap.put("message", "Designation is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Designation is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Designation is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(designation ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "Designation is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Designation is not Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Designation is not Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		String bg = jenilUserDto.getBloodgroup();
		if(null==bg ){
			hashmap.put("code", 404);
			hashmap.put("message", "Bloodgroup is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Bloodgroup is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Bloodgroup is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(bg ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "Bloodgroup is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Bloodgroup is not Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Bloodgroup is not Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		String gender = jenilUserDto.getGender();
		if(null==gender ){
			hashmap.put("code", 404);
			hashmap.put("message", "Gender is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Gender is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Gender is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(gender ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "Gender is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Gender is not Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Gender is not Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		boolean male = gender.equals("Male");
		boolean female = gender.equals("Female");
		if(!(male || female)) {
			hashmap.put("code", 404);
			hashmap.put("message", "Gender has only Male or Female.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Gender has only Male or Female");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Gender has only Male or Female");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		String address = jenilUserDto.getAddress();
		if(null==address ){
			hashmap.put("code", 404);
			hashmap.put("message", "Address is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Address is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Address is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(address ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "Address is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Address is not Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Address is not Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		String role = jenilUserDto.getRole();
		if(null==role ){
			hashmap.put("code", 404);
			hashmap.put("message", "Role is not Available.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Role is not Available");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Role is not Available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		if(role ==""){
			hashmap.put("code", 404);
			hashmap.put("message", "Role is Empty.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Role is not Empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Role is not Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		
		boolean uservalid = role.equals("ROLE_USER");
		boolean adminvalid = role.equals("ROLE_ADMIN");
		if(!(uservalid || adminvalid)) {
			hashmap.put("code", 404);
			hashmap.put("message", "Role has only ROLE_USER or ROLE_ADMIN.");
			hashmap.put("status", false);
			logger.error("log ERROR {}","Role has only ROLE_USER or ROLE_ADMIN.");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Role has only ROLE_USER or ROLE_ADMIN.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
		}
		
		
/////////////////////////////////////////////////////////////////////////////////////////////////
		
		else {
			
			JenilUserDetail jenilUserDetail = modelMapper.map(jenilUserDto, JenilUserDetail.class);
			
			jenilUserDetail.setUpdatetime(dtf.format(ldt));
			jenilUserRepo.save(jenilUserDetail);
			logger.info("log Info {}","User has updated successfully");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "User has updated successfully");
			return ResponseEntity.ok(response.updateUseraddMap(jenilUserDetail));
		}
	}	
	
	
}
