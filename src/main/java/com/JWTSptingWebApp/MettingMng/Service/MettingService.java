
package com.JWTSptingWebApp.MettingMng.Service;

import org.springframework.stereotype.Service;

import com.JWTSptingWebApp.MettingMng.Dto.MettingDto;
import com.JWTSptingWebApp.MettingMng.Model.Metting;


@Service
public interface MettingService {

	public void createMeeting(Metting meeting);
	public MettingDto convertModelToDto (Metting metting);
	 public Metting convertDtoToModel(MettingDto mettingDto);

	//public void saveMeetingAttendees(UserDetailDto2 userDto2, Metting meeting);
}

