package com.JWTSptingWebApp.BlogMng.ServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.JWTSptingWebApp.BlogMng.Controller.BlogController;
import com.JWTSptingWebApp.BlogMng.Dto.BlogDto;
import com.JWTSptingWebApp.BlogMng.Model.Blog;
import com.JWTSptingWebApp.BlogMng.Model.BlogCoverImage;
import com.JWTSptingWebApp.BlogMng.Repository.BlogRepository;
import com.JWTSptingWebApp.BlogMng.Repository.CoverImageRepository;
import com.JWTSptingWebApp.BlogMng.Service.BlogService;
import com.JWTSptingWebApp.UserDetail.Repository.UserDetailRepository;
import com.JWTSptingWebApp.UserDetail.model.UserDetail;
import com.JWTSptingWebApp.logging.service.LoggingService;
import com.JWTSptingWebApp.singlefileupload.exception.FileStorageException;
import com.JWTSptingWebApp.singlefileupload.model.DBFile;
import com.JWTSptingWebApp.singlefileupload.repository.DBFileRepository;

@Service
public class BlogServiceImpl implements BlogService {
	@Autowired
	private BlogRepository blogRepository;
	static Logger log = LoggerFactory.getLogger(BlogServiceImpl.class);
	@Autowired
	public LoggingService loggingService;
	@Autowired
	private CoverImageRepository coverImageRepository;
	@Autowired
	DBFileRepository dBFileRepository;
	@Autowired
	private CoverImageRepository coverImgRepo;
	@Autowired
	private UserDetailRepository userRepo;
	
	public Blog createBlog(BlogDto blogDto) {
		Blog blog = convertDtoToModelBlog(blogDto);
		blogRepository.save(blog);
		return blog;
	}

	public void updateBlog(BlogDto blogDto) {
		Blog blog = convertDtoToModelBlog(blogDto);
		blogRepository.save(blog);
	}

	public Blog convertDtoToModelBlog(BlogDto blogDto) {
		Blog blog = new Blog();
		blog.setToken(blogDto.getToken());
		blog.setTitle(blogDto.getTitle());
		blog.setContent(blogDto.getContent());
		blog.setHashTags(blogDto.getHashTags());
		blog.setAuthorToken(blogDto.getAuthorToken());
		blog.setCreatedTime(blogDto.getCreatedTime());
		return blog;
	}

	public BlogDto convertModelToDtoBlog(Blog blog) {
		BlogDto blogDto = new BlogDto();
		blogDto.setToken(blog.getToken());
		blogDto.setTitle(blog.getTitle());
		blogDto.setContent(blog.getContent());
		blogDto.setHashTags(blog.getHashTags());
		blogDto.setAuthorToken(blog.getAuthorToken());
		blogDto.setCreatedTime(blog.getCreatedTime());
		return blogDto;
	}

	public ResponseEntity<?> deleteFile(String authortoken, String token) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (authortoken.isEmpty()) {
			map.put("code", 404);
			map.put("status", false);
			map.put("data", authortoken);
			map.put("message", "AuthorToken required");

			loggingService.createLog("NullPointerException", new Timestamp(System.currentTimeMillis()), false, 404, "AuthorToken must not be null");

			log.info("log INFO {}", "AuthorToken must not be null");
			return ResponseEntity.ok(map);
		}
		if (token.isEmpty()) {
			map.put("code", 404);
			map.put("status", false);
			map.put("data", token);
			map.put("message", "Blog Token required");

			loggingService.createLog("NullPointerException", new Timestamp(System.currentTimeMillis()), false, 404, "BlogToken must not be null");

			log.info("log INFO {}", "BlogToken must not be null");
			return ResponseEntity.ok(map);
		}
		try {
			String author = blogRepository.authortoken(authortoken, token);
			if (author == null) {
				map.put("code", 404);
				map.put("status", false);
				map.put("message", "it's Unauthorized or not found Blog");
				map.put("data", token);

				loggingService.createLog("NoSuchElementException", new Timestamp(System.currentTimeMillis()), false, 404, "Blog not found");

				log.info("log INFO {}", "Blog not found");
				return ResponseEntity.ok(map);
			} else {
				map.put("code", 200);
				map.put("status", true);
				map.put("message", "deleted");
				map.put("data", author);
				dBFileRepository.deletefile(token);
				blogRepository.deleteblogbytoken(authortoken, token);
				coverImageRepository.deleteByBlogtoken(token);
				loggingService.createLog("_", new Timestamp(System.currentTimeMillis()), true, 200, "Blog deleted successfully");
				log.info("log INFO {}", "Blog deleted successfully");
								return ResponseEntity.ok(map);
			}
		} catch (NoSuchElementException e) {
			map.put("code", 404);
			map.put("status", false);
			map.put("message", "not found token");
			map.put("data", token);
			return ResponseEntity.ok(map);
		}
	}

	public ResponseEntity<?> getAllBlog() {
		List<Blog> blog = blogRepository.findAll();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> response = new HashMap<String, Object>();
		if (null == blog && blog.isEmpty()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", 404);
			map.put("message", "No Post(s) Found");
			map.put("status", "False");

			loggingService.createLog("NoSuchElementException", new Timestamp(System.currentTimeMillis()), false, 404, "Blogs not found");

			log.info("log INFO {}", "Blogs not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		} else {
			for (int i = 0; i < blog.size(); i++) {				
				Map<String, Object> map = new HashMap<String, Object>();
				BlogCoverImage imageDetail = coverImageRepository.findByBlogtoken(blog.get(i).getToken());
				if(imageDetail != null)
					map.put("coverImage", imageDetail.getDownloadUrl());
				else
					map.put("coverImage", "Not available");
				
				UserDetail userDetail = userRepo.findByTokenuserAndActive(blog.get(i).getAuthorToken(),true);
				if(userDetail != null) {
					map.put("token", blog.get(i).getToken());
					map.put("title", blog.get(i).getTitle());
					map.put("createdTime", blog.get(i).getCreatedTime());
					map.put("authorToken", blog.get(i).getAuthorToken());
					list.add(map);
					response.put("status", true);
					response.put("code", 200);
					response.put("message", "Got all blogs successfully");
					response.put("data", list);
					loggingService.createLog("_", new Timestamp(System.currentTimeMillis()), true, 200, "Blogs viewed successfully");
					log.info("log INFO {}", "Blogs viewed successfully");
				}
			}
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}

	@Override
	public ResponseEntity<?> viewPost(String token) {
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> responseData = new HashMap<String, Object>();
		List<String> fileNames = new ArrayList<String>();
		List<String> fileUrls = new ArrayList<String>();
		List<String> fileTokens = new ArrayList<String>();
		if (!(blogRepository.existsByToken(token))) {
			response.put("code", 404);
			response.put("message", "Blog not found");
			response.put("status", false);
			response.put("token", token);

			loggingService.createLog("NoSuchElementException", new Timestamp(System.currentTimeMillis()), false, 404, "Blog not found");

			log.info("log INFO {}", "Blog not found");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(response);
		}
		else {
			Blog blog = blogRepository.findByToken(token);
			
			List<DBFile> file = dBFileRepository.findByblogToken(token);
			for(DBFile f : file) {
				fileNames.add(f.getFileName());
				fileUrls.add(f.getDownloadUrl());
				fileTokens.add(f.getToken());
			}
			if(fileNames.isEmpty() || fileUrls.isEmpty() || fileTokens.isEmpty()) {
				response.put("fileNames","Not available");
				response.put("fileUrls","Not available");
				response.put("fileTokens","Not available");
			}
			else {
				response.put("fileNames",fileNames);
				response.put("fileUrls",fileUrls);
				response.put("fileTokens",fileTokens);
			}
			BlogCoverImage imageDetail = coverImageRepository.findByBlogtoken(token);
			if(imageDetail != null)
			{
				response.put("coverImageUrl", imageDetail.getDownloadUrl());
				response.put("coverImageName",imageDetail.getImageName());
				response.put("coverImageToken", imageDetail.getImagetoken());
			}
				
			else
				response.put("coverImage", "Not available");
			
			UserDetail user = userRepo.findByTokenuserAndActive(blog.getAuthorToken(),true);
			
			if(user != null) {
				response.put("authorName",user.getUsername());
				response.put("title", blog.getTitle());
				response.put("authorToken", blog.getAuthorToken());
				response.put("createdTime", blog.getCreatedTime());
				response.put("hashTags", blog.getHashTags());
				response.put("content", blog.getContent());
				response.put("authorName",user.getUsername());
				responseData.put("data", response);
				responseData.put("status", true);
				responseData.put("code", 200);
				responseData.put("message", "post shown successfully");
				loggingService.createLog("_", new Timestamp(System.currentTimeMillis()), true, 200, "Post viewed successfully");
				log.info("log INFO {}", "Post viewed successfully");
			}
			else {
				responseData.put("status", false);
				responseData.put("code", 404);
				responseData.put("message", "Not available or may be deleted user account");
				responseData.put("data", token);

				loggingService.createLog("NoSuchElementException", new Timestamp(System.currentTimeMillis()), false, 404, "Author not found");

				log.info("log INFO {}", "Author not found");
			}
			return ResponseEntity.ok(responseData);
		}
	}	
	
	private static String LOCAL_DIRECTORY = "C://FILESTORE//";
	
	public BlogCoverImage storeimage(MultipartFile file) {

		String uuid = UUID.randomUUID().toString();
		String uid = uuid.toString().replaceFirst("[a-z]", uuid.toString().toUpperCase()).replace("-", "")
				.replaceFirst("[a-z]", uuid.toString().toLowerCase());
		String generatedToken = uid.substring(29, 37);

		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String dateTime = now.format(myFormatObj).replace(" ", "_").replace(":", "_");
		
		String updatedName = "file_cover_"+generatedToken + "_" + dateTime;
		
		try {

			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			
			String originalFileName = file.getOriginalFilename();

			String extension = "";

			int i = originalFileName.lastIndexOf('.');
			if (i > 0) {
				extension = originalFileName.substring(i + 1);
			}
			Path path = Paths.get(LOCAL_DIRECTORY + updatedName + "." + extension);

			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (updatedName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + updatedName);
			}

			String originalFileName = file.getOriginalFilename();

			String extension = "";
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/blogImage/downloadCoverImage/")
					.path(generatedToken).toUriString();
			int i = originalFileName.lastIndexOf('.');
			if (i > 0) {
				extension = originalFileName.substring(i + 1);
			}
			BlogCoverImage coverimage=new BlogCoverImage(generatedToken,  updatedName + "." + extension, file.getContentType(),
					file.getBytes(), fileDownloadUri);
			
					BlogCoverImage bi = coverImageRepository.save(coverimage);
					BlogCoverImage blogCoverImage = coverImageRepository.findByImagetoken(generatedToken);
					blogCoverImage.getDownloadUrl();
			
			return bi;
			
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + updatedName + ". Please try again!", ex);
		}	
	}
	public BlogCoverImage getFile(String fileToken) {
		return (BlogCoverImage) coverImageRepository.findByImagetoken(fileToken);
	}
}