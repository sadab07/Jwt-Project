package com.JWTSptingWebApp.BlogMng.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.JWTSptingWebApp.BlogMng.Model.BlogCoverImage;
import com.JWTSptingWebApp.BlogMng.Repository.CoverImageRepository;
import com.JWTSptingWebApp.BlogMng.Service.BlogService;
import com.JWTSptingWebApp.singlefileupload.Dto.UploadFileResponse;

@RestController
@RequestMapping("/blogImage")
public class CoverImageController {

	@Autowired
	CoverImageRepository coverImageRepository;
	
	@Autowired
	BlogService BlogService;
	
	@PostMapping("/uploadCoverImage")
	public Map<String, Object> uploadFile(@RequestParam("image") MultipartFile file) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (file.getSize() > 0 || !file.isEmpty()) {
			
			BlogCoverImage blogCoverImage = BlogService.storeimage(file);
			map.put("response", new UploadFileResponse(blogCoverImage.getImageName(),blogCoverImage.getDownloadUrl() , blogCoverImage.getImageType(),
					file.getSize(), blogCoverImage.getImagetoken()));
			map.put("status", true);
		} else {
			map.put("message", "Please enter Image");
			map.put("status", false);
			return map;
		}
		map.put("message", "successfully uploaded");
		return map;
	}
	
	@GetMapping("/downloadCoverImage/{imgtoken}")
	public ResponseEntity<?> downloadFile(@PathVariable String imgtoken) {
		// Load file from database
		BlogCoverImage blogCoverImage = BlogService.getFile(imgtoken);
		Map<String, Object> map = new HashMap<String, Object>();
		if (blogCoverImage == null) {
			map.put("code", 404);
			map.put("message", "Image not found");
			map.put("status", false);
			map.put("token", imgtoken);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		} else {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(blogCoverImage.getImageType()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + blogCoverImage.getImageName() + "\"")
					.body(new ByteArrayResource(blogCoverImage.getData()));
		}
	}
}
