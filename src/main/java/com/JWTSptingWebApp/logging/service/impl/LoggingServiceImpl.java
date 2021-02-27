package com.JWTSptingWebApp.logging.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JWTSptingWebApp.logging.model.LoggingDetail;
import com.JWTSptingWebApp.logging.repository.LoggingRepository;
import com.JWTSptingWebApp.logging.service.LoggingService;

@Service
public class LoggingServiceImpl implements LoggingService{

	@Autowired
	public LoggingRepository loggingRepository;
	
	@Override
	public void createLog(String exception, Timestamp creationtime, boolean status,int code,String message) {
		LoggingDetail loggingDetail = new LoggingDetail();
		loggingDetail.setException(exception);
		loggingDetail.setCreationtime(creationtime);
		loggingDetail.setStatus(status);
		loggingDetail.setCode(code);
		loggingDetail.setMessage(message);
		loggingRepository.save(loggingDetail);
	}

}
