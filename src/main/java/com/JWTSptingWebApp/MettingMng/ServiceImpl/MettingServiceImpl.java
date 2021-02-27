package com.JWTSptingWebApp.MettingMng.ServiceImpl;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.JWTSptingWebApp.MettingMng.Dto.MettingDto;
import com.JWTSptingWebApp.MettingMng.Model.Metting;
import com.JWTSptingWebApp.MettingMng.Service.MettingService;
import com.JWTSptingWebApp.MettingMng.repository.MettingRepository;
import com.JWTSptingWebApp.UserDetail.ServiceImpl.UserDetailMasterServiceImpl;


@Service
public class MettingServiceImpl implements MettingService {
	
	@Autowired
	private MettingRepository mettingRepository;
	
	@Autowired 
	UserDetailMasterServiceImpl userDetailMasterServiceImpl;
	
	
	public void createMeeting(Metting metting) {
		
		mettingRepository.save(metting);
	}
	 
	 public Metting convertDtoToModel(MettingDto mettingDto) {
			
			Metting meeting=new Metting();
			meeting.setToken(mettingDto.getToken());
			meeting.setSubject(mettingDto.getSubject());
			meeting.setDescription(mettingDto.getDescription());
			meeting.setProjectname(mettingDto.getProjectname());
			meeting.setStarttime(mettingDto.getStarttime());
			meeting.setEndtime(mettingDto.getEndtime());
			return meeting;
		}
	public MettingDto convertModelToDto (Metting metting) {
		
		 MettingDto meetingdto=new MettingDto();
			meetingdto.setToken(metting.getToken());
			meetingdto.setSubject(metting.getSubject());
			meetingdto.setDescription(metting.getDescription());
			meetingdto.setProjectname(metting.getProjectname());
			meetingdto.setStarttime(metting.getStarttime());
			meetingdto.setEndtime(metting.getEndtime());
			return meetingdto;
	}

	
}
