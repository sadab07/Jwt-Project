package com.JWTSptingWebApp.UserDetail.ServiceImpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.JWTSptingWebApp.UserDetail.Dto.UserDetailDto;
import com.JWTSptingWebApp.UserDetail.Dto.UserDetailDto2;
import com.JWTSptingWebApp.UserDetail.Dto.UserDetailDto3;
import com.JWTSptingWebApp.UserDetail.Repository.UserDetailRepository;
import com.JWTSptingWebApp.UserDetail.Service.UserDetailMasterService;
import com.JWTSptingWebApp.UserDetail.model.UserDetail;

@Service
@Transactional
public class UserDetailMasterServiceImpl implements UserDetailMasterService {
	@Autowired
	private UserDetailRepository userDetailRepository;
	 @Autowired
	    private JavaMailSender javaMailSender;
	@Override
	public void createUserDetail(UserDetailDto userDetailDto) {
		UserDetail userDetail = convertDtoToModel(userDetailDto);
		userDetailRepository.save(userDetail);

	}

	private UserDetail convertDtoToModel(UserDetailDto userDetailDto) {
		UserDetail userDetail = new UserDetail();
		if (userDetailDto.getUserid() != 0) {
			userDetail.setUserid(userDetailDto.getUserid());
		}
		userDetail.setFirstName(userDetailDto.getFirstName());
		userDetail.setLastName(userDetailDto.getLastName());
		userDetail.setEmail(userDetailDto.getEmail());
		userDetail.setUsername(userDetailDto.getUsername());
		userDetail.setContact(userDetailDto.getContact());
		userDetail.setDesignation(userDetailDto.getDesignation());
		userDetail.setBloodGroup(userDetailDto.getBloodGroup());
		userDetail.setGender(userDetailDto.getGender());
		userDetail.setAddress(userDetailDto.getAddress());
		userDetail.setPassword(userDetailDto.getPassword());
		userDetail.setTokenuser(userDetailDto.getTokenuser());
		userDetail.setCreatedTime(userDetailDto.getCreatedTime());
		userDetail.setUpdateTime(userDetailDto.getUpdateTime());
		//userDetail.setRole(userDetailDto.getRole());
		userDetail.setRole("ROLE_USER");
		userDetail.setActive(true);
		return userDetail;
	}
	//
	@Override
	public List<UserDetailDto2> getAllUserDetail() {
		List<UserDetail> list = (List<UserDetail>) userDetailRepository.findAll();
		
		List<UserDetail> listactive=new ArrayList();
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i).isActive()==true)
			{
				listactive.add(list.get(i));
			}
		}
		List<UserDetailDto2> userDetaillist = listactive.stream().map(UserDetailDto2::new)
				.collect(Collectors.toCollection(ArrayList::new));
		return userDetaillist;
	}

	public ResponseEntity<?> updateUserDetail(UserDetailDto userDetailDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserDetail userDetail;
		userDetail = convertDtoToModel(userDetailDto);
		userDetailRepository.save(userDetail);
		return ResponseEntity.ok(map);
	}

	private UserDetailDto convertModelToDTO(UserDetail userDetail) {

		UserDetailDto userDetailDto = new UserDetailDto();

		userDetailDto.setFirstName(userDetailDto.getFirstName());
		userDetailDto.setLastName(userDetailDto.getLastName());
		userDetailDto.setEmail(userDetailDto.getEmail());
		userDetailDto.setUsername(userDetailDto.getUsername());
		userDetailDto.setContact(userDetailDto.getContact());
		userDetailDto.setDesignation(userDetailDto.getDesignation());
		userDetailDto.setBloodGroup(userDetailDto.getBloodGroup());
		userDetailDto.setGender(userDetailDto.getGender());
		userDetailDto.setAddress(userDetailDto.getAddress());
		userDetailDto.setPassword(userDetailDto.getPassword());
		userDetailDto.setUserid(userDetailDto.getUserid());
		userDetailDto.setCreatedTime(userDetailDto.getCreatedTime());
		userDetailDto.setUpdateTime(userDetailDto.getUpdateTime());
		userDetailDto.setActive(userDetailDto.isActive());
		// userDetailDto.setToken(userDetailDto.getToken());

		return userDetailDto;
	}

	public UserDetailDto2 convertModelToDTO1(UserDetail userDetail) {

		UserDetailDto2 userDetailDto = new UserDetailDto2();

		userDetailDto.setFirstName(userDetail.getFirstName());
		userDetailDto.setLastName(userDetail.getLastName());
		userDetailDto.setEmail(userDetail.getEmail());
		userDetailDto.setUsername(userDetail.getUsername());
		userDetailDto.setContact(userDetail.getContact());
		userDetailDto.setDesignation(userDetail.getDesignation());
		
		userDetailDto.setTokenuser(userDetail.getTokenuser());
		userDetailDto.setCreatedTime(userDetail.getCreatedTime());
		return userDetailDto;
	}
	public UserDetailDto2 convertDTOToDTO1(UserDetailDto userDetail) {

		UserDetailDto2 userDetailDto = new UserDetailDto2();

		userDetailDto.setFirstName(userDetail.getFirstName());
		userDetailDto.setLastName(userDetail.getLastName());
		userDetailDto.setEmail(userDetail.getEmail());
		userDetailDto.setUsername(userDetail.getUsername());
		userDetailDto.setContact(userDetail.getContact());
		userDetailDto.setDesignation(userDetail.getDesignation());
		
		userDetailDto.setTokenuser(userDetail.getTokenuser());
		userDetailDto.setCreatedTime(userDetail.getCreatedTime());
		return userDetailDto;
	}
	@Override
	public void deleteUserDetail(String token) {
		UserDetail user=userDetailRepository.findByTokenuserAndActive(token, true);
		user.setActive(false);
		userDetailRepository.save(user);
	}
	private UserDetailDto3 convertModelToDTO2(UserDetail userDetail) {
		UserDetailDto3 userDetailDto = new UserDetailDto3();
		userDetailDto.setFirstName(userDetail.getFirstName());
		userDetailDto.setLastName(userDetail.getLastName());
		userDetailDto.setEmail(userDetail.getEmail());
		userDetailDto.setUsername(userDetail.getUsername());
		userDetailDto.setContact(userDetail.getContact());
		userDetailDto.setDesignation(userDetail.getDesignation());
		userDetailDto.setBloodGroup(userDetail.getBloodGroup());
		userDetailDto.setGender(userDetail.getGender());
		userDetailDto.setAddress(userDetail.getAddress());
		
		userDetailDto.setTokenuser(userDetail.getTokenuser());
		return userDetailDto;
	}

	@Override
	public List<UserDetailDto3> findAllUserByTokenuser(String token) {
		List<UserDetail> list = (List<UserDetail>) userDetailRepository.findAllByTokenuser(token);

		List<UserDetail> listactive=new ArrayList();
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i).isActive()==true)
			{
				listactive.add(list.get(i));
			}
		}
		List<UserDetailDto3> userDetaillist = listactive.stream().map(this::convertModelToDTO2)
				.collect(Collectors.toCollection(ArrayList::new));
		return userDetaillist;
	}

	
	public void sendEmail(String recipient, String firstname, String lastname, Timestamp createdTime) {
		String pattern = "dd MMMM yyyy,HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String dateTime = simpleDateFormat.format(createdTime);
		SimpleMailMessage msg = new SimpleMailMessage();
//		Object mimeMessage = null;
//		MimeMessageHelper helper = new MimeMessageHelper((MimeMessage) mimeMessage, "utf-8");
//		String htmlMsg = "<h3>Hello World!</h3>";
//	
//		MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
//        String htmlMsg = "<body style='border:2px solid black'>"
//                    +"Your onetime password for registration is  " 
//                        + "Please use this OTP to complete your new user registration."+
//                          "OTP is confidential, do not share this  with anyone.</body>";
//        message.setContent(htmlMsg, "text/html");
		
        msg.setTo(recipient);
        msg.setSubject("Management Tool Welcomes "+firstname+" "+lastname);
        msg.setText("Hello "+firstname+" "+lastname+"\n\nYour User has been created on Management tool on "+dateTime+"\nThank You\nManagement Tool");
      
        
        javaMailSender.send(msg);

	}

	
}
//	with use of array
//	public String[][] getAllUserDetail() {
//		List<UserDetail> list = userDetailRepository.findAll();
//		List<UserDetailDto> userDetaillist = list.stream().map(UserDetailDto::new).collect(Collectors.toCollection(ArrayList::new));
//		Map<String, Object> map = new HashMap<String, Object>();
//		String[][] user=new String[userDetaillist.size()][7];
//		int i;
//		// List <String> users = new ArrayList<String>();
//		for(i=0;i<userDetaillist.size();i++)
//		{
//			 user[i][0]="FirsName :"+userDetaillist.get(i).getFirstName();
//			 user[i][1]="LastName :"+userDetaillist.get(i).getLastName();
//			 user[i][2]="Email :"+userDetaillist.get(i).getEmail();
//			 user[i][3]="Username :"+userDetaillist.get(i).getUsername();
//			 user[i][4]="Contact :"+userDetaillist.get(i).getContact();
//			 user[i][5]="Designation :"+userDetaillist.get(i).getDesignation();
//			 user[i][6]="Token :"+ userDetaillist.get(i).getToken();
//		 }
//
//		map.put("Data", user);
//		return user;
//	}
