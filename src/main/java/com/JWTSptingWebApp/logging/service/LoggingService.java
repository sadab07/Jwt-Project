package com.JWTSptingWebApp.logging.service;

import java.sql.Timestamp;

public interface LoggingService {

	void createLog(String exception,Timestamp creationtime,boolean status,int code,String message);
}
