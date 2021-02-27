package com.JWTSptingWebApp.jenilsinglefile.service;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface JenilFileService {

	ResponseEntity<?> uploadFile(MultipartFile file);

	ResponseEntity<?> uploadMultipleFiles(MultipartFile[] files);

	ResponseEntity<?> downloadFile(String filetoken, HttpServletRequest request) throws MalformedURLException;

	ResponseEntity<?> deleteFile(String filetoken);

	ResponseEntity<?> getFiles(boolean active);

}
