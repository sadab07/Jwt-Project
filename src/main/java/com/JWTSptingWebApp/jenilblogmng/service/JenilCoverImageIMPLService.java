package com.JWTSptingWebApp.jenilblogmng.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
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

import com.JWTSptingWebApp.jenilblogmng.dto.JenilCoverImageDto;
import com.JWTSptingWebApp.jenilblogmng.model.JenilCoverImage;
import com.JWTSptingWebApp.jenilblogmng.repository.JenilCoverImageRepository;
import com.JWTSptingWebApp.jenilsinglefile.model.JenilFileDetail;
import com.JWTSptingWebApp.logging.service.LoggingService;

@Service
public class JenilCoverImageIMPLService implements JenilCoverImageService{

	@Autowired
	JenilCoverImageRepository imagerepo;
	
	@Autowired
	LoggingService loggingService;
	
	Logger logger = LoggerFactory.getLogger(JenilCoverImageIMPLService.class);
	
	ModelMapper modelmapper = new ModelMapper();
	
	String UPLOAD_DIR="C:\\Users\\ZeronSec\\coverimage\\";
	
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
	public ResponseEntity<?> uploadImage(MultipartFile image) {
		LinkedHashMap<String , Object> map = new LinkedHashMap<String, Object>();
		if(image.isEmpty())
		{
			map.put("code", 404);
			map.put("message", "Image is not found");
			map.put("status", false);
			logger.error("log ERROR {}","CoverImage is not found");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "CoverImage is not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
		String imagetoken = generateRandom();
		String imagename=StringUtils.cleanPath(image.getOriginalFilename());
		String url=ServletUriComponentsBuilder.fromCurrentContextPath().path("/coverimage/download").path("?imagetoken=").path(imagetoken).build().toUriString();
		
		JenilCoverImageDto dto = new JenilCoverImageDto();
		dto.setImagetoken(imagetoken);
		dto.setImagename(imagename);
		dto.setImagepath(UPLOAD_DIR+image.getOriginalFilename());
		dto.setActive(true);
		dto.setDownloadurl(url);

		JenilCoverImage coimage = modelmapper.map(dto, JenilCoverImage.class);
		imagerepo.save(coimage);
		
		if(StoreFile(image)) {
			map.put("imagename", coimage.getImagename());
			map.put("downloadUrl", coimage.getDownloadurl());
			map.put("imagetoken", coimage.getImagetoken());
		}
		LinkedHashMap<String, Object> outmap = new LinkedHashMap<String, Object>();
		outmap.put("code", 200);
		outmap.put("status", true);
		outmap.put("message", "CoverImage Upload");
		outmap.put("data", map);
		logger.info("log Info {}","CoverImage has Uploaded");
		loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "CoverImage has Uploaded");
		return ResponseEntity.ok(outmap);
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

	@Override
	public ResponseEntity<?> downloadImage(String imagetoken, HttpServletRequest request) throws MalformedURLException {
		
		Resource resource = Loadimage(imagetoken);
		String contentType=null ;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("log Info {}","CoverImage has Downloaded");
		loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "CoverImage has Downloaded");
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attchment; filename=\""+ resource.getFilename() + "\"").body(resource);
	}
	
	public Resource Loadimage(String imagetoken) throws MalformedURLException
	{
		Optional<JenilCoverImage> coimage = imagerepo.findByImagetoken(imagetoken);
		JenilCoverImage cimage = coimage.get();
		
		Path path = Paths.get(cimage.getImagepath().toString());
		
		Resource resource = (Resource) new UrlResource(path.toUri());
		
		return resource;
	}
}
