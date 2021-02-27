package com.JWTSptingWebApp.springbootsecurity.service.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.JWTSptingWebApp.UserDetail.Repository.UserDetailRepository;
import com.JWTSptingWebApp.UserDetail.model.UserDetail;
import com.JWTSptingWebApp.logging.service.LoggingService;
import com.JWTSptingWebApp.mailutility.Mail;
import com.JWTSptingWebApp.mailutility.MailService;
import com.JWTSptingWebApp.springbootsecurity.model.OTPDetail;
import com.JWTSptingWebApp.springbootsecurity.model.UserDTO;

import com.JWTSptingWebApp.springbootsecurity.repository.OTPRepository;

import com.JWTSptingWebApp.springbootsecurity.service.UserDetailsService;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
public UserDetailRepository userDetailRepository;
	@Autowired
	public OTPRepository otpRepository;

	@Autowired
	MailService mailService = null;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	/*
	 * @Autowired HttpServletRequest request;
	 */

	@Autowired
	HttpServletResponse reponse;

	@Autowired
	public LoggingService loggingService;

	private final String X_REQUEST_ID = "X-Request-ID";

	static Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);

	// HttpSession session = request.getSession(false) ;

	@Override
	public ResponseEntity<?> forgotPassword(String email,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Timestamp timestamp = null;
		try {
			log.info("path: {}, method: {}, query {}", request.getRequestURI(), request.getMethod(),
					request.getQueryString());
			String xRequestId = request.getHeader(X_REQUEST_ID);
			log.error("{}",xRequestId);
			String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			boolean emailValid = email.matches(regex);

			if (emailValid == false) {
				map.put("code", 400);
				map.put("message", "Email Address Not Valid.");
				map.put("status", false);
				map.put("email", email);
				log.error("log ERROR {}", "Email Address Not Valid.");
				loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400,
						"Email Address Not Valid.");
				ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
			}
			UserDetail userdetail = userDetailRepository.findByEmailAndActive(email, true);
			OTPDetail otpDetail = new OTPDetail();

			if (userdetail == null) {
				map.put("code", 404);
				map.put("message", "We didn't find an account for that e-mail address");
				map.put("status", false);
				log.error("log ERROR {}", "We didn't find an account for that e-mail address");
				loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 404,
						"We didn't find an account for that e-mail address");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
			} else {
				String response = userdetail.getEmail();
				if (!response.startsWith("Invalid")) {
					Mail mail = new Mail();
					mail.setMailFrom("rc7200875@gmail.com");
					mail.setMailTo(email);
					mail.setMailSubject("OTP for Reset Password..!");
					String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
					mail.setMailContent("Your OTP for Reset Password is : " + otp + " \n\nThanks");
					userDetailRepository.save(userdetail);

					otpDetail = otpRepository.findByUserid(userdetail.getUserid());

					if (null == otpDetail) {
						otpDetail = new OTPDetail();
					}
					otpDetail.setUserid(userdetail.getUserid());
					otpDetail.setOtp(otp);
					timestamp = new Timestamp(System.currentTimeMillis());
					otpDetail.setOtpcreationtime(timestamp);
					otpRepository.save(otpDetail);

					mailService.sendEmail(mail);
					log.info("log INFO {}", "Mail send successfully..");
				}

			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			map.put("code", 400);
			map.put("message", "Illegal Argument");
			map.put("status", false);
			log.error("log ERROR {}", e.getMessage());
			loggingService.createLog("IllegalArgumentException", new Timestamp(System.currentTimeMillis()), false, 500,
					e.getMessage());
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("code", 200);
		map.put("message", "OTP Send Successfully to registered email");
		map.put("status", true);
		map.put("otpCreationTime", timestamp);
		log.info("log INFO {}", "OTP Send Successfully to registered email");
		loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200,
				"OTP Send Successfully to registered email");
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	@Override
	public ResponseEntity<?> verifyOtp(String otp) {
		Map<String, Object> map = new HashMap<String, Object>();
		Timestamp timestamp = null;
		OTPDetail otpDetail = new OTPDetail();
		Optional<UserDetail> userdetail = null;
		try {
			otpDetail = otpRepository.findByOtp(otp);

			if (otpDetail == null) {
				map.put("code", 400);
				map.put("message", "Invalid OTP");
				map.put("status", false);
				log.error("log ERROR {}", "Invalid OTP");
				loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Invalid OTP");
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
			} else {
				userdetail = userDetailRepository.findById(otpDetail.getUserid());

				if (null == userdetail) {
					map.put("code", 404);
					map.put("message", "We didn't find an account");
					map.put("status", false);
					log.error("log ERROR {}", "We didn't find an account");
					loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 404,
							"We didn't find an account");
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
				} else {
					timestamp = otpDetail.getOtpcreationtime();

					Timestamp oldTimestamp = new Timestamp(
							new Date(timestamp.getTime() + TimeUnit.MINUTES.toMillis(5)).getTime());

					Timestamp newTimestamp = new Timestamp(new Date(System.currentTimeMillis()).getTime());

					// final int Fiv_MINUTES = 5 * 60 * 1000;
					// long fiveMinAgo = System.currentTimeMillis() - Fiv_MINUTES;

					if (oldTimestamp.getTime() <= newTimestamp.getTime()) {
						map.put("code", 408);
						map.put("message", "OTP session timeout");
						map.put("status", false);
						log.error("log ERROR {}", "OTP session timeout");
						loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 408,
								"OTP session timeout");
						return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(map);
					} else {
						otpDetail.setOtpverify(1);
						otpRepository.save(otpDetail);
					}
				}

			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			map.put("code", 400);
			map.put("message", "Illegal Argument");
			map.put("status", false);
			log.error("log ERROR {}", e.getMessage());
			loggingService.createLog("IllegalArgumentException", new Timestamp(System.currentTimeMillis()), false, 500,
					e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 500);
			map.put("message", e.getMessage());
			map.put("status", false);
			log.error("log ERROR {}", e.getMessage());
			loggingService.createLog("Exception", new Timestamp(System.currentTimeMillis()), false, 500,
					e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
		// session.setAttribute("email", userdetail.getEmail());
		// request.getSession().setAttribute("email", userdetail.getEmail());
		map.put("code", 200);
		map.put("message", "OTP Verify Successfully");
		map.put("status", true);
		map.put("username", userdetail.get().getUsername());
		log.info("log INFO {}", "OTP Verify Successfully");
		loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "OTP Verify Successfully");
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	@Override
	public ResponseEntity<?> changePassword(UserDTO user) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserDetail newUser = new UserDetail();
			OTPDetail otpDetail = new OTPDetail();
			String pass = user.getNewpassword();
			String cpass = user.getConfirmpassword();
			String username = user.getUsername();
			if (null == pass) {
				map.put("code", 400);
				map.put("message", "Password Required.");
				map.put("status", false);
				log.error("log ERROR {}", "Password Required.");
				loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400,
						"Password Required.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
			}
			if (null == cpass) {
				map.put("code", 400);
				map.put("message", "Confirm Password Required");
				map.put("status", false);
				log.error("log ERROR {}", "Confirm Password Required");
				loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400,
						"Confirm Password Required");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
			}
			if (null == username) {
				map.put("code", 400);
				map.put("message", "Username Required");
				map.put("status", false);
				log.error("log ERROR {}", "Username Required");
				loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400,
						"Username Required");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
			}
			// String email = (String) session.getAttribute("email");
			// String email = (String) request.getSession().getAttribute("email");
			if (!pass.equals(cpass)) {
				map.put("code", 400);
				map.put("message", "Passwords do not match.");
				map.put("status", false);
				log.error("log ERROR {}", "Passwords do not match.");
				loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400,
						"Passwords do not match.");
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(map);
			}
			newUser = userDetailRepository.findByUsernameAndActive(username, true);
			if (null != newUser) {
				otpDetail = otpRepository.findByUseridAndOtpverify(newUser.getUserid(), 1);

				if (null == otpDetail) {
					map.put("code", 400);
					map.put("message", "otp not Verified. try again");
					map.put("status", false);
					log.error("log ERROR {}", "otp not Verified. try again");
					loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400,
							"otp not Verified. try again");
					return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
				} else {
					int otp = otpDetail.getOtpverify();
					if (otp == 0) {
						map.put("code", 400);
						map.put("message", "otp not Verified. try again");
						map.put("status", false);
						log.error("log ERROR {}", "otp not Verified. try again");
						loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400,
								"otp not Verified. try again");
						return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
					} else {
						newUser.setPassword(bcryptEncoder.encode(user.getNewpassword()));
						otpDetail.setOtpverify(0);
						otpRepository.save(otpDetail);
						map.put("code", 200);
						map.put("message", "change Password Successfully");
						map.put("status", true);
						log.info("log INFO {}", "change Password Successfully");
						loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200,
								"change Password Successfully");
						map.put("data", userDetailRepository.save(newUser));
					}
				}

			} else {
				map.put("code", 404);
				map.put("message", "User Not Found");
				map.put("status", false);
				log.error("log ERROR {}", "User Not Found");
				loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 404, "User Not Found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
			}

		} catch (NonUniqueResultException e) {
			e.printStackTrace();
			map.put("code", 409);
			map.put("message", "Non Unique Result Exception");
			map.put("status", false);
			log.error("log ERROR {}", e.getMessage());
			loggingService.createLog("NonUniqueResultException", new Timestamp(System.currentTimeMillis()), false, 500,
					e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			map.put("code", 400);
			map.put("message", "Illegal Argument");
			map.put("status", false);
			log.error("log ERROR {}", e.getMessage());
			loggingService.createLog("IllegalArgumentException", new Timestamp(System.currentTimeMillis()), false, 500,
					e.getMessage());
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 400);
			map.put("message", "BAD_REQUEST");
			map.put("status", false);
			log.error("log ERROR {}", "BAD_REQUEST");
			loggingService.createLog("BAD_REQUEST", new Timestamp(System.currentTimeMillis()), false, 400,
					e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
	//private ModelMapper modelMapper = new ModelMapper();
	// Post post = modelMapper.map(postDto, Post.class);

}
