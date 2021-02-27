package com.JWTSptingWebApp.springbootsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JWTSptingWebApp.springbootsecurity.model.OTPDetail;

public interface OTPRepository extends JpaRepository<OTPDetail,Long>{

	public OTPDetail findByOtp(String otp);
	
	public OTPDetail findByUserid(long userid);
	
	public OTPDetail findByUseridAndOtpverify(long userid,int otpverify);
}
