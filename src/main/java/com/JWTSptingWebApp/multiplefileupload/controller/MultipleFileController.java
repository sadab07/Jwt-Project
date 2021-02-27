package com.JWTSptingWebApp.multiplefileupload.controller;

import antlr.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.JWTSptingWebApp.singlefileupload.Dto.UploadFileResponse;
import com.JWTSptingWebApp.singlefileupload.exception.FileStorageException;
import com.JWTSptingWebApp.singlefileupload.model.DBFile;
import com.JWTSptingWebApp.singlefileupload.repository.DBFileRepository;
//import com.JWTSptingWebApp.singlefileupload.model.DBFile;
//import com.JWTSptingWebApp.singlefileupload.repository.DBFileRepository;
//import com.JWTSptingWebApp.singlefileupload.service.DBFileStorageService;
import com.JWTSptingWebApp.singlefileupload.service.DBFileStorageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@RestController
@Transactional
@RequestMapping("/files")
public class MultipleFileController {

	@Autowired
	private DBFileStorageService dbFileStorageService;

	@Autowired
	private UploadFileResponse fileResponse;

	@Autowired
	private DBFile dbFiles;

	@Autowired
	private DBFileRepository dbFileRepo;

	@PostMapping("/uploadMultipleFiles")
//  public List<Map<String, Object>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
	public Map<String, Object> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (Arrays.asList(files).size() > 1) {
//		Array[Arrays.asList(files).size()] a[] = new Array();
			List<UploadFileResponse> fileArray=new ArrayList<>();
			for(int i=0;i<files.length;i++)
			{
				MultipartFile file=files[i];
				DBFile dbFile = dbFileStorageService.storeFile(file);
				
				UploadFileResponse res=new UploadFileResponse(dbFile.getFileName(), dbFile.getDownloadUrl(), 
						file.getContentType(), file.getSize(),dbFile.getToken());
				fileArray.add(res);
				
			}
			map.put("data", fileArray);
		}

		else {
			map.put("message", "Please enter multiple file");
			map.put("status", false);
			map.put("code", 200);
			return map;
		}
		map.put("message", "successfully uploaded");
		map.put("status", true);
		map.put("code", 200);
		return map;
	}
}