package com.JWTSptingWebApp.jenilblogmng.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.JWTSptingWebApp.jenilblogmng.controller.JenilBlogController;
import com.JWTSptingWebApp.jenilblogmng.dto.JenilBlogDto;
import com.JWTSptingWebApp.jenilblogmng.dto.JenilCoverImageDto;
import com.JWTSptingWebApp.jenilblogmng.model.JenilBlog;
import com.JWTSptingWebApp.jenilblogmng.model.JenilCoverImage;
import com.JWTSptingWebApp.jenilblogmng.model.Response;
import com.JWTSptingWebApp.jenilblogmng.repository.JenilBlogRepository;
import com.JWTSptingWebApp.jenilblogmng.repository.JenilCoverImageRepository;
import com.JWTSptingWebApp.jenilsinglefile.model.JenilFileDetail;
import com.JWTSptingWebApp.jenilsinglefile.repository.JenilFileRepository;
import com.JWTSptingWebApp.jenilsinglefile.service.JenilFileIMPLService;
import com.JWTSptingWebApp.jeniluserdetail.model.JenilUserDetail;
import com.JWTSptingWebApp.jeniluserdetail.repository.JenilUserDetailRepository;
import com.JWTSptingWebApp.logging.service.LoggingService;

@Service
public class JenilBlogIMPLService implements JenilBlogService{

	Logger logger = LoggerFactory.getLogger(JenilBlogIMPLService.class);
	
	@Autowired
	JenilBlogRepository blogRepo;
	
	@Autowired
	JenilUserDetailRepository jenilUserRepo;
	
	@Autowired
	JenilFileRepository jenilFileRepo;
	
	@Autowired
	JenilCoverImageRepository jenilcorepo;
	
	@Autowired
	JenilFileIMPLService fileser;
	
	@Autowired
	LoggingService loggingService;
	
	Response response = new Response();
	ModelMapper modelMapper = new ModelMapper();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime ldt = LocalDateTime.now();
	
	
	
	private static String generateRandom() {
		String aToZ="01234ABCDEFGHIJKLMNO012345PQRSTUVWXYZ678901235abcdefghijklmnopqrstuvwxyz6789";
	    
		Random random=new Random();
	    StringBuilder res=new StringBuilder();
	    
	    for (int i = 0; i < 8; i++) {
	    	int randomIndex=random.nextInt(aToZ.length());
	    	res.append(aToZ.charAt(randomIndex));
	        }
		return	res.toString();
	}
	
	
	
	@Override
	public ResponseEntity<?> createBlog(JenilBlogDto dto) {
		String method = "createBlog";
		logger.info(method+"called");
		
		dto.setBlogtoken(generateRandom());
		dto.setCreatedtime(dtf.format(ldt));
		dto.setActive(true);
		
		LinkedHashMap<String , Object> ermap = new LinkedHashMap<String, Object>();
		JenilUserDetail user = jenilUserRepo.findByUsertoken(dto.getAuthortoken());
		if(user==null) {
			ermap.put("code", 404);
			ermap.put("status", false);
			ermap.put("message", "User is not found");
			logger.error("log ERROR {}","User is not found");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "User is not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
		}
		
		if(dto.getBlogtitle().isEmpty()) {
			ermap.put("code", 404);
			ermap.put("status", false);
			ermap.put("message", "BlogTitle is empty");
			logger.error("log ERROR {}","Blog title is empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Blog title is empty");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
		}
		
		if(dto.getBlogcontent().isEmpty()) {
			ermap.put("code", 404);
			ermap.put("status", false);
			ermap.put("message", "BlogContent is empty");
			logger.error("log ERROR {}","Blog Content is empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Blog Content is empty");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
		}
		
		if(dto.getHashtags().isEmpty()) {
			ermap.put("code", 404);
			ermap.put("status", false);
			ermap.put("message", "Hashtags is empty");
			logger.error("log ERROR {}","Hashtags is empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Hashtags is empty");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
		}
		
		if(dto.getAuthortoken().isEmpty()) {
			ermap.put("code", 404);
			ermap.put("status", false);
			ermap.put("message", "Authortoken is empty");
			logger.error("log ERROR {}","AuthorToken is empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "AuthorToken is empty");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
		}
		
		boolean active = user.getActive();
		if(active==false) {
			ermap.put("code", 404);
			ermap.put("message", "Author has been Deleted");
			ermap.put("status", false);
			logger.error("log ERROR {}","Author has been Deleted");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Author has been Deleted");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ermap);
		}
		for(int i=0;i<dto.getFiletoken().length;i++) {
			JenilFileDetail files = jenilFileRepo.findByFiletoken(dto.getFiletoken()[i]);
			boolean factive = files.getActive();
		
//			if(files==null) {
//				ermap.put("code", 404);
//				ermap.put("status", false);
//				ermap.put("message", "File is not found");
//				logger.error("log ERROR {}","File is not found");
//				loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "File is not found");
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
//			}
		
			if(files.getBlogtoken()!=null) {
				ermap.put("code", 404);
				ermap.put("status", false);
				ermap.put("message", "File has been used");
				logger.error("log ERROR {}","File has been Used");
				loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "File has been Used");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
			}
			if(factive==false) {
				ermap.put("code", 404);
				ermap.put("status", false);
				ermap.put("message", "File has been Deleted");
				logger.error("log ERROR {}","File has been Deleted");
				loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "File has been Deleted");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
			}else {
				files.setBlogtoken(dto.getBlogtoken());
			}
		}
		
		Optional<JenilCoverImage> coimage = jenilcorepo.findByImagetoken(dto.getImagetoken());
		JenilCoverImage cimage = coimage.get();
		if(null==cimage) {
			ermap.put("code", 404);
			ermap.put("status", false);
			ermap.put("message", "CoverImage is not found");
			logger.error("log ERROR {}","CoverImage is not found");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "CoverImage is not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
		}
		if(cimage.getActive()==false) {
			ermap.put("code", 404);
			ermap.put("status", false);
			ermap.put("message", "Image has been Deleted");
			logger.error("log ERROR {}","CoverImage has been Deleted");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "CoverImage has been Deleted");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
		}
		
		if(cimage.getBlogtoken()!=null) {
			ermap.put("code", 404);
			ermap.put("status", false);
			ermap.put("message", "CoverImage has been Used");
			logger.error("log ERROR {}","CoverImage has been Used");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "CoverImage has been Used");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
		}else {
			cimage.setBlogtoken(dto.getBlogtoken());
		}
		
	
		JenilBlog blog = modelMapper.map(dto, JenilBlog.class);
		blogRepo.save(blog);
		
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		map.put("blogtoken", blog.getBlogtoken());
		map.put("blogtitle", blog.getBlogtitle());
		map.put("blogcontent", blog.getBlogcontent());
		map.put("hashtags", blog.getHashtags());
		map.put("authortoken", blog.getAuthortoken());
		map.put("authorname", user.getUsername());
		map.put("coverimagename", cimage.getImagename());
		map.put("filetoken", dto.getFiletoken());
		map.put("cretedtime", blog.getCreatedtime());
		logger.info("log Info {}","Blog has been created");
		loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "Blog has been created");
		return ResponseEntity.ok(response.addMap(map));
	}



	@Override
	public ResponseEntity<?> deleteBlog(String blogtoken) {
		LinkedHashMap<String , Object> map = new LinkedHashMap<String, Object>();
		if(blogtoken.isEmpty() )
		{
			map.put("code", 404);
			map.put("message", "Blog is not found");
			map.put("status", false);
			logger.error("log ERROR {}","Blog is not found");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Blog is not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
		JenilBlog blog = blogRepo.findByToken(blogtoken);
		if(null==blog) {
			map.put("code", 404);
			map.put("message", "Blog is not found");
			map.put("status", false);
			logger.error("log ERROR {}","Blog is not found");
			loggingService.createLog("Blogtoken is not wrong", new Timestamp(System.currentTimeMillis()), false, 400, "Blog is not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
		blog.setActive(false);
		
		JenilCoverImage coimage = jenilcorepo.findByBlogtoken(blogtoken);
		if(null==coimage) {
			map.put("code", 404);
			map.put("message", "Blog has not CoverImage");
			map.put("status", false);
			logger.error("log ERROR {}","Blog has not CoverImage");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Blog has not CoverImage");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}else {
			coimage.setActive(false);
		}
		
		List<JenilFileDetail> file = jenilFileRepo.findByBlogtoken(blogtoken);
		for(JenilFileDetail f:file) {
			if(null==file) {
				map.put("code", 404);
				map.put("message", "Blog has not uploaded any file");
				map.put("status", false);
				logger.error("log ERROR {}","Blog has not uploaded any file");
				loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Blog has not uploaded any file");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
			}else {
				f.setActive(false);
			}
		}
		
		blogRepo.save(blog);
		logger.info("log Info {}","Blog has been deleted");
		loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "Blog has been deleted");
		return ResponseEntity.ok(response.deleteMap(blogtoken));
	}



	@Override
	public ResponseEntity<?> updateBlog(String blogtoken , JenilBlogDto dto) {
		LinkedHashMap<String , Object> ermap = new LinkedHashMap<String, Object>();
		JenilUserDetail user = jenilUserRepo.findByUsertoken(dto.getAuthortoken());
		if(user==null) {
			ermap.put("code", 404);
			ermap.put("status", false);
			ermap.put("message", "User is not found");
			logger.error("log ERROR {}","User is not found");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "User is not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
		}
		boolean bactive  = dto.getActive();
		if(bactive==false) {
			ermap.put("code", 404);
			ermap.put("status", false);
			ermap.put("message", "Blog has been Deleted");
			logger.error("log ERROR {}","Blog has been Deleted");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Blog has been Deleted");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
		}
		
		if(dto.getBlogtitle().isEmpty()) {
			ermap.put("code", 404);
			ermap.put("status", false);
			ermap.put("message", "BlogTitle is empty");
			logger.error("log ERROR {}","BlogTitle is empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "BlogTitle is empty");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
		}
		
		if(dto.getBlogcontent().isEmpty()) {
			ermap.put("code", 404);
			ermap.put("status", false);
			ermap.put("message", "BlogContent is empty");
			logger.error("log ERROR {}","BlogContent is empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "BlogContent is empty");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
		}
		
		if(dto.getHashtags().isEmpty()) {
			ermap.put("code", 404);
			ermap.put("status", false);
			ermap.put("message", "Hashtags is empty");
			logger.error("log ERROR {}","Hashtags is empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Hashtags is empty");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
		}
		
		if(dto.getAuthortoken().isEmpty()) {
			ermap.put("code", 404);
			ermap.put("status", false);
			ermap.put("message", "Authortoken is empty");
			logger.error("log ERROR {}","Authortoken is empty");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Authortoken is empty");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
		}
		
		boolean active = user.getActive();
		if(active==false) {
			ermap.put("code", 404);
			ermap.put("message", "Author has been Deleted");
			ermap.put("status", false);
			logger.error("log ERROR {}","Author has been Deleted");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Author has been Deleted");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ermap);
		}
		for(int i=0;i<dto.getFiletoken().length;i++) {
		JenilFileDetail file = jenilFileRepo.findByFiletoken(dto.getFiletoken()[i]);
			if(null==file) {
				ermap.put("code", 404);
				ermap.put("status", false);
				ermap.put("message", "Filetoken is not found");
				ermap.put("authortoken", dto.getFiletoken());
				logger.error("log ERROR {}","Filetoken is not found");
				loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Filetoken is not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
			}
			if(file.getBlogtoken()!=null) {
				ermap.put("code", 404);
				ermap.put("message", "File is already exist");
				ermap.put("status", false);
				logger.error("log ERROR {}","File is already exist");
				loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "File is already exist");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ermap);
			}
		}
		JenilCoverImage coimage = jenilcorepo.findByBlogtoken(blogtoken);
		if(null==coimage) {
			ermap.put("code", 404);
			ermap.put("message", "Blog has not CoverImage");
			ermap.put("status", false);
			logger.error("log ERROR {}","Blog has not CoverImage");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Blog has not CoverImage");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ermap);
		}
		if(coimage.getBlogtoken()!=null) {
			ermap.put("code", 404);
			ermap.put("message", "CoverImage is already exist");
			ermap.put("status", false);
			logger.error("log ERROR {}","CoverImage is already exist");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "CoverImage is already exist");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ermap);
		}
		
		JenilBlog blog = modelMapper.map(dto, JenilBlog.class);
		blogRepo.save(blog);
		
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		map.put("blogtoken", blog.getBlogtoken());
		map.put("blogtitle", blog.getBlogtitle());
		map.put("blogcontent", blog.getBlogcontent());
		map.put("hashtags", blog.getHashtags());
		map.put("authortoken", blog.getAuthortoken());
		map.put("authorname", user.getUsername());
		map.put("filetoken", dto.getFiletoken());
		map.put("updateTime", dtf.format(ldt));
		logger.info("log Info {}","Blog has been updated");
		loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "Blog has been updated");
		return ResponseEntity.ok(response.updateBlogaddMap(map));
	}



	@Override
	public ResponseEntity<?> getAllBlog(boolean active) {
		List<JenilBlog> blogs = blogRepo.findByActive(active);
		logger.info("log Info {}","AllBlogs have been shown");
		loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "AllBlogs have been shown");
		return ResponseEntity.ok(response.addMapGetBlogs(blogs));
	}

	String UPLOAD_DIR="C:\\Users\\ZeronSec\\UploadedFIles\\";



	@Override
	public ResponseEntity<?> viewpostBlog(String blogtoken) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		JenilBlog blog = blogRepo.findByToken(blogtoken);
		if(blog==null) {
			map.put("code", 404);
			map.put("status", false);
			map.put("message", "Blog is not found");
			logger.error("log ERROR {}","Blog is not found");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Blog is not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
		if(blog.getActive()==false) {
			map.put("code", 404);
			map.put("message", "Blog has been Deleted already");
			map.put("status", false);
			logger.error("log ERROR {}","Blog has been Deleted already");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "Blog has been Deleted already");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
		
		JenilUserDetail user = jenilUserRepo.findByUsertoken(blog.getAuthortoken());
		if(user==null) {
			map.put("code", 404);
			map.put("status", false);
			map.put("message", "User is not found");
			logger.error("log ERROR {}","User is not found");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "User is not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
		if(user.getActive()==false) {
			map.put("code", 404);
			map.put("message", "User has been Deleted already");
			map.put("status", false);
			logger.error("log ERROR {}","User has been Deleted already");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "User has been Deleted already");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
		
		
		JenilCoverImage coimage = jenilcorepo.findByBlogtoken(blogtoken);
		if(null==coimage){
			map.put("code", 404);
			map.put("message", "This blog has not CoverImage");
			map.put("status", false);
			logger.error("log ERROR {}","This blog has not CoverImage");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "This blog has not CoverImage");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
		
		
		
		map.put("blogtitle", blog.getBlogtitle());
		map.put("blogcontent", blog.getBlogcontent());
		map.put("hashtags", blog.getHashtags());
		map.put("authorname", user.getUsername());
		map.put("coverimagename",coimage.getImagename());
		map.put("coverimagedownload", coimage.getDownloadurl());
		map.put("createdtime", blog.getCreatedtime());
		map.put("authortoken", blog.getAuthortoken());
		
		List<String> filename = new ArrayList<String>();
		List<String> filedownload = new ArrayList<String>();
		
		List<JenilFileDetail> file = jenilFileRepo.findByBlogtoken(blogtoken);
		for(JenilFileDetail f:file) {
			filename.add(f.getFilename());
			filedownload.add(f.getDownloadurl());
		}
		if(null==file) {
			map.put("code", 404);
			map.put("message", "This blog has not uploaded any File");
			map.put("status", false);
			logger.error("log ERROR {}","This blog has not uploaded any File");
			loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), false, 400, "This blog has not uploaded any File");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}else {
			map.put("filename", filename);
			map.put("filedownload", filedownload);
		}
		logger.info("log Info {}","View post successfully shown");
		loggingService.createLog("-", new Timestamp(System.currentTimeMillis()), true, 200, "View post successfully shown");
		return ResponseEntity.ok(response.viewpostBlogaddMap(map));
	}

	
}
