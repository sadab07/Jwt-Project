package com.JWTSptingWebApp.jenilsinglefile.controller;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.JWTSptingWebApp.jenilsinglefile.service.JenilFileService;

@RestController
@RequestMapping("/file")
public class JenilFileController {

	@Autowired
	JenilFileService jenilser;
	
	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file){
		return jenilser.uploadFile(file); 
	}
	
	@PostMapping("/multiUpload")
	public ResponseEntity<?> uploadMultipleFiles(@RequestParam("file") MultipartFile[] files){
		return jenilser.uploadMultipleFiles(files);
	}
	
	@GetMapping("/download")
	public ResponseEntity<?> downloadFile(@RequestParam String filetoken, HttpServletRequest request) throws MalformedURLException{
		return jenilser.downloadFile(filetoken, request);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> deleteFile(@RequestParam String filetoken){
		return jenilser.deleteFile(filetoken);
	}
	
	@PostMapping("/getAll")
	public ResponseEntity<?> getFiles(boolean active){
		return jenilser.getFiles(active);
	}
}
