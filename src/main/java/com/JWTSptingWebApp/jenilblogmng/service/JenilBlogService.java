package com.JWTSptingWebApp.jenilblogmng.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.JWTSptingWebApp.jenilblogmng.dto.JenilBlogDto;
import com.JWTSptingWebApp.jenilblogmng.dto.JenilCoverImageDto;

public interface JenilBlogService {

	ResponseEntity<?> createBlog(JenilBlogDto dto);

	ResponseEntity<?> deleteBlog(String blogtoken);

	ResponseEntity<?> updateBlog(String blogtoken, JenilBlogDto dto);

	ResponseEntity<?> getAllBlog(boolean active);

	ResponseEntity<?> viewpostBlog(String blogtoken);

	
	

	


}
