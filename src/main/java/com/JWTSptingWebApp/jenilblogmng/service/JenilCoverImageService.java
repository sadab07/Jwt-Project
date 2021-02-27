package com.JWTSptingWebApp.jenilblogmng.service;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface JenilCoverImageService {

	ResponseEntity<?> uploadImage(MultipartFile image);

	ResponseEntity<?> downloadImage(String imagetoken, HttpServletRequest request) throws MalformedURLException;

}
