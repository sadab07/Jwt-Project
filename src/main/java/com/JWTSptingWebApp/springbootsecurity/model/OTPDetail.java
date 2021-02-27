package com.JWTSptingWebApp.springbootsecurity.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otpdetail")
public class OTPDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "otpid", nullable = false, updatable = false)
	private long otpid;
	
	@Column(name = "userid", nullable = false, updatable = false)
	private long userid;
	
	@Column(name = "otp")
	private String otp;
	
	@Column(columnDefinition = "TIMESTAMP")
	private Timestamp otpcreationtime;
	
	@Column(name = "otpverify")
	private int otpverify;

	public long getOtpid() {
		return otpid;
	}

	public void setOtpid(long otpid) {
		this.otpid = otpid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Timestamp getOtpcreationtime() {
		return otpcreationtime;
	}

	public void setOtpcreationtime(Timestamp otpcreationtime) {
		this.otpcreationtime = otpcreationtime;
	}

	public int getOtpverify() {
		return otpverify;
	}

	public void setOtpverify(int otpverify) {
		this.otpverify = otpverify;
	}
	
}
