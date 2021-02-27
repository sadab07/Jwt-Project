package com.JWTSptingWebApp.jenilsinglefile.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.JWTSptingWebApp.jenilblogmng.service.JenilCoverImageIMPLService;
import com.JWTSptingWebApp.jenilsinglefile.dto.JenilFileDetailDto;
import com.JWTSptingWebApp.jenilsinglefile.model.JenilFileDetail;
import com.JWTSptingWebApp.jenilsinglefile.model.Response;
import com.JWTSptingWebApp.jenilsinglefile.repository.JenilFileRepository;
import com.JWTSptingWebApp.logging.service.LoggingService;

@Service
public class JenilFileIMPLService implements JenilFileService{

	@Autowired
	JenilFileRepository jenilRepo;
	
	@Autowired
	LoggingService loggingService;
	
	Logger logger = LoggerFactory.getLogger(JenilFileIMPLService.class);
	
	String UPLOAD_DIR="C:\\Users\\ZeronSec\\UploadedFIles\\";
	
	ModelMapper modelMapper = new ModelMapper();
	Response response = new Response();
	
	private static String generateRandom() {
		String aToZ="01234ABCDEFGHIJKLMNO012345PQRSTUVWXYZ678901235abcdefghijklmnopqrstuvwxyz6789";
	    
		Random rand=new Random();
	    StringBuilder res=new StringBuilder();
	    
	    for (int i = 0; i < 8; i++) {
	    	int randIndex=rand.nextInt(aToZ.length());
	    	res.append(aToZ.charAt(randIndex));
	        }
		return	res.toString();
	}
	
	
	
	@Override
	public ResponseEntity<?> uploadFile(MultipartFile file) {
		
		LinkedHashMap<String , Object> map = new LinkedHashMap<String, Object>();
		if(file.isEmpty())
		{
			map.put("code", 404);
			map.put("message", "File is not found");
			map.put("status", false);
			logger.error("log ERROR {}","File is not found");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "File is not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
		String filetoken=generateRandom();
		String filename=StringUtils.cleanPath(file.getOriginalFilename());
		String filetype = file.getContentType();
		String url=ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/download").path("?filetoken=").path(filetoken).build().toUriString();
		
		JenilFileDetailDto dto = new JenilFileDetailDto();
		dto.setFiletoken(filetoken);
		dto.setFilename(filename);
		dto.setFiletype(filetype);
		dto.setDownloadurl(url);
		dto.setFilepath(UPLOAD_DIR+file.getOriginalFilename());
		dto.setActive(true);
		
		JenilFileDetail detail = modelMapper.map(dto, JenilFileDetail.class);
		jenilRepo.save(detail);
		
		if(StoreFile(file)) {
			map.put("filename", detail.getFilename());
			map.put("filetype", detail.getFiletype());
			map.put("downloadUrl", detail.getDownloadurl());
			map.put("filetoken", detail.getFiletoken());
		}
		logger.info("log Info {}","File has Uploaded");
		loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "File has Uploaded");
		return ResponseEntity.ok(response.addMap(map));
	}
	
	
	
	@Override
	public ResponseEntity<?> downloadFile(String filetoken ,  HttpServletRequest request) throws MalformedURLException {
		Resource resource = LoadFile(filetoken);
		String contentType=null ;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attchment; filename=\""+ resource.getFilename() + "\"").body(resource);
	}

	
	
	
	@Override
	public ResponseEntity<?> uploadMultipleFiles(MultipartFile[] files) {
		LinkedHashMap<String , Object> inmap = new LinkedHashMap<String, Object>();
	
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		for(MultipartFile file:files) {
			if(file.isEmpty())
			{
				inmap.put("code", 404);
				inmap.put("message", "File is not found");
				inmap.put("status", false);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(inmap);
			}
		}
		for(MultipartFile file:files) {
			LinkedHashMap<String , Object> map = new LinkedHashMap<String, Object>();
			if(StoreFile(file)) {
				String filetoken=generateRandom();
				String filename=StringUtils.cleanPath(file.getOriginalFilename());
				String filetype = file.getContentType();
				String url=ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/download").path("?filetoken=").path(filetoken).build().toUriString();
			
				JenilFileDetailDto dto = new JenilFileDetailDto();
				dto.setFiletoken(filetoken);
				dto.setFilename(filename);
				dto.setFiletype(filetype);
				dto.setDownloadurl(url);
				dto.setFilepath(UPLOAD_DIR+file.getOriginalFilename());
				dto.setActive(true);
			
				JenilFileDetail detail = modelMapper.map(dto, JenilFileDetail.class);
				jenilRepo.save(detail);
				map.put("filename", detail.getFilename());
				map.put("filetype", detail.getFiletype());
				map.put("downloadUrl", detail.getDownloadurl());
				map.put("filetoken", detail.getFiletoken());
				
				list.add(map);
				}
			}
		logger.info("log Info {}","Files have Uploaded");
		loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "Files have Uploaded");
		return ResponseEntity.ok(response.addMapMultiFiles(list));
	}
	
	
	
	
	public Boolean StoreFile(MultipartFile mufile)
	{
		boolean f=false;

		try {
			Files.copy(mufile.getInputStream(), Paths.get(UPLOAD_DIR+ File.separator+mufile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			f=true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}

	
	
	
	public Resource LoadFile(String filetoken) throws MalformedURLException
	{
		Optional<JenilFileDetail> fdetail = jenilRepo.findByToken(filetoken);
		JenilFileDetail detail = fdetail.get();
		
		Path path = Paths.get(detail.getFilepath().toString());
		
		Resource resource = (Resource) new UrlResource(path.toUri());
		
		return resource;
	}



	public ResponseEntity<?> deleteFile(String filetoken) {
		LinkedHashMap<String , Object> map = new LinkedHashMap<String, Object>();
		if(filetoken.isEmpty() )
		{
			map.put("code", 404);
			map.put("message", "File is not found");
			map.put("status", false);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
		
		Optional<JenilFileDetail> opdetail=jenilRepo.findByToken(filetoken);
		JenilFileDetail detail = opdetail.get();
		detail.setActive(false);
		jenilRepo.save(detail);
		logger.info("log Info {}","File has Deleted succesfully");
		loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "File has Deleted succesfully");
		return ResponseEntity.ok(response.addMapDeleteFile(filetoken));
	}



	@Override
	public ResponseEntity<?> getFiles(boolean active) {
		List<JenilFileDetail> ldetail = jenilRepo.findByActive(active);
		logger.info("log Info {}","AllFiles get succesfully");
		loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "AllFiles get succesfully");
		return ResponseEntity.ok(response.addMapGetFiles(ldetail));
	}
	
	
	
}
