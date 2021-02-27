
package com.JWTSptingWebApp.singlefileupload.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ByteArrayResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.JWTSptingWebApp.singlefileupload.Dto.UploadFileResponse;

import com.JWTSptingWebApp.singlefileupload.model.DBFile;
import com.JWTSptingWebApp.singlefileupload.repository.DBFileRepository;
import com.JWTSptingWebApp.singlefileupload.service.DBFileStorageService;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

@RestController
@Transactional
@RequestMapping("/file")
public class SingleFileController {

	@Autowired
	private DBFileStorageService dbFileStorageService;

	@Autowired
	private DBFileRepository dbFileRepo;

	@PostMapping("/uploadFile")
	public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (file.getSize() > 0 || !file.isEmpty()) {
//			System.err.println(file.getSize()/1000000 + " MB");
//			String fileSize = file.getSize()/1000000 + " MB";
//			if(fileSize)
//			{
//				
//			}
			DBFile dbFile = dbFileStorageService.storeFile(file);

			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/downloadFile/")
					.path(dbFile.getToken()).toUriString();

			map.put("response", new UploadFileResponse(dbFile.getFileName(), fileDownloadUri, file.getContentType(),
					file.getSize(), dbFile.getToken()));
			map.put("status", true);
		} else {
			map.put("message", "Please enter file");
			map.put("status", false);
			return map;
		}
		map.put("message", "successfully uploaded");
		return map;
	}
	
	@GetMapping("/downloadFile/{fileToken}")
	public ResponseEntity<?> downloadFile(@PathVariable String fileToken) {
		// Load file from database
		DBFile dbFile = dbFileStorageService.getFile(fileToken);
		Map<String, Object> map = new HashMap<String, Object>();
		if (dbFile == null) {
			map.put("code", 404);
			map.put("message", "File not found");
			map.put("status", false);
			map.put("token", fileToken);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		} else {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(dbFile.getFileType()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
					.body(new ByteArrayResource(dbFile.getData()));
		}
	}
	
	@DeleteMapping("/deleteFile/{filetoken}")
	public ResponseEntity<Map<String, Object>> deleteFile(@PathVariable String filetoken) {
		Map<String, Object> maps = new HashMap<String, Object>();
		Map<String,Object> map = new HashMap<String,Object>();
//    	return ResponseEntity.ok(map);
		DBFile dbFile = (DBFile) dbFileRepo.findByToken(filetoken);
		if (dbFile == null) {
			maps.put("code", 404);
			maps.put("message", "File not found");
			maps.put("status", false);
			maps.put("token", filetoken);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		} else {
			dbFileRepo.deleteByToken(filetoken);
			map.put("file", dbFile.getFileName());
			maps.put("code", 200);
			maps.put("data", map);
			maps.put("message", "Delete Successfully");
			maps.put("status", true);
			return ResponseEntity.ok(maps);
		}
	}
}

//	@PostMapping("/uploadMultipleFiles")
////  public List<Map<String, Object>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//	public Map<String, Object> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//		Map<String, Object> map = new HashMap<String, Object>();
