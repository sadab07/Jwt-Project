package com.JWTSptingWebApp.logging.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "loggingdetail")
public class LoggingDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "loggingid", nullable = false, updatable = false, length = 8)
	private long loggingid;

	@Column(name = "exception", length = 1024)
	private String exception;

	@Column(columnDefinition = "TIMESTAMP")
	private Timestamp creationtime;

	@Column(name = "status", length = 24)
	private boolean status;

	@Column(name = "code", length = 24)
	private int code;

	@Column(name = "message", length = 512)
	private String message;

	public long getLoggingid() {
		return loggingid;
	}

	public void setLoggingid(long loggingid) {
		this.loggingid = loggingid;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
