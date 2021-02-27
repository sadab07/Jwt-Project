package com.JWTSptingWebApp.jenilblogmng.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.JWTSptingWebApp.jenilblogmng.dto.JenilBlogDto;
import com.JWTSptingWebApp.jenilblogmng.service.JenilBlogService;

@RestController
@RequestMapping("/jenilblog")
public class JenilBlogController {

	Logger logger = LoggerFactory.getLogger(JenilBlogController.class);
	
	@Autowired
	JenilBlogService jenilblogser;
	
	@PostMapping("/create")
	public ResponseEntity<?> createBlog(@RequestBody JenilBlogDto dto){
		String method = "createBlog";
		logger.info(method+"called");
		return jenilblogser.createBlog(dto);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> deleteBlog(@RequestParam String blogtoken){
		return jenilblogser.deleteBlog(blogtoken);
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> updateBlog(@RequestParam String blogtoken , @RequestBody JenilBlogDto dto){
		return jenilblogser.updateBlog(blogtoken, dto);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllBlog(boolean active){
		return jenilblogser.getAllBlog(active);
	}
	
	@PostMapping("/viewpost")
	public ResponseEntity<?> viewpostBlog(@RequestParam String blogtoken){
		return jenilblogser.viewpostBlog(blogtoken);
	}
}
