package com.JWTSptingWebApp.jenilblogmng.controller;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.JWTSptingWebApp.jenilblogmng.dto.JenilCoverImageDto;
import com.JWTSptingWebApp.jenilblogmng.service.JenilBlogService;
import com.JWTSptingWebApp.jenilblogmng.service.JenilCoverImageService;

@RestController
@RequestMapping("/coverimage")
public class JenilCoverImageController {

	@Autowired
	JenilCoverImageService imageser;
	
	@PostMapping("/upload")
	public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile image){
		return imageser.uploadImage(image);
	}
	
	@GetMapping("/download")
	public ResponseEntity<?> downloadImage(@RequestParam String imagetoken, HttpServletRequest request) throws MalformedURLException{
		return imageser.downloadImage(imagetoken, request);
	}
}
