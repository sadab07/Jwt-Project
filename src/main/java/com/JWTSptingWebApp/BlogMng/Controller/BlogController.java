
package com.JWTSptingWebApp.BlogMng.Controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.JWTSptingWebApp.BlogMng.Dto.BlogDto;

import com.JWTSptingWebApp.BlogMng.Model.Blog;
import com.JWTSptingWebApp.BlogMng.Model.BlogCoverImage;
import com.JWTSptingWebApp.BlogMng.Repository.BlogRepository;
import com.JWTSptingWebApp.BlogMng.Repository.CoverImageRepository;
import com.JWTSptingWebApp.BlogMng.Service.BlogService;
import com.JWTSptingWebApp.BlogMng.ServiceImpl.BlogServiceImpl;
import com.JWTSptingWebApp.UserDetail.Repository.UserDetailRepository;
import com.JWTSptingWebApp.UserDetail.model.UserDetail;
import com.JWTSptingWebApp.logging.service.LoggingService;
import com.JWTSptingWebApp.singlefileupload.model.DBFile;
import com.JWTSptingWebApp.singlefileupload.repository.DBFileRepository;
import com.JWTSptingWebApp.springbootsecurity.controller.AuthenticationController;

@RestController
@Transactional
@RequestMapping("/blogPost")

public class BlogController {

	static Logger log = LoggerFactory.getLogger(BlogController.class);

	@Autowired
	public LoggingService loggingService;

	@Autowired
	private BlogServiceImpl blogServiceImpl;

	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	UserDetailRepository userDetailRepository;

	@Autowired
	CoverImageRepository coverImageRepository;

	@Autowired
	DBFileRepository dBFileRepository;

	DBFile dbFile;

	BlogCoverImage blogCoverImage;

	@Autowired
	private BlogService blogService;

	@PostMapping("/createBlog")
	public ResponseEntity<?> createBlog(@RequestBody BlogDto blogDto) {
		// System.err.println("A");
		Map<String, Object> map = new HashMap<String, Object>();
		UUID uuid = UUID.randomUUID();
		String uid = uuid.toString().replaceFirst("[a-z]", uuid.toString().toUpperCase()).replace("-", "")
				.replaceFirst("[a-z]", uuid.toString().toLowerCase());
		String token = uid.substring(29, 37);

		blogDto.setToken(token);
		Timestamp createdTime = new Timestamp(System.currentTimeMillis());
		String pattern = "dd MMMM yyyy,HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String dateTime = simpleDateFormat.format(createdTime);
		blogDto.setCreatedTime(dateTime);

		if (blogDto.getTitle().isEmpty()) {
			map.put("code", 404);
			map.put("message", "Title Required.");
			map.put("status", false);
			map.put("title", blogDto.getTitle());
			loggingService.createLog("NullPointerException", new Timestamp(System.currentTimeMillis()), false, 404,
					"Title must not be null");

			log.info("log INFO {}", "Title must not be null");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		if (blogDto.getContent().isEmpty()) {
			map.put("code", 404);
			map.put("message", "Content Required.");
			map.put("status", false);
			map.put("content", blogDto.getContent());

			loggingService.createLog("NullPointerException", new Timestamp(System.currentTimeMillis()), false, 404,
					"Content must not be null");

			log.info("log INFO {}", "Content must not be null");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		if (blogDto.getHashTags().isEmpty()) {
			map.put("code", 404);
			map.put("message", "Hashtag Required.");
			map.put("status", false);
			map.put("hashtag", blogDto.getHashTags());

			loggingService.createLog("NullPointerException", new Timestamp(System.currentTimeMillis()), false, 404,
					"Hashtags must not be null");

			log.info("log INFO {}", "Hashtags must not be null");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}

		if (blogDto.getAuthorToken().isEmpty()) {
			map.put("code", 404);
			map.put("message", "Author Required.");
			map.put("status", false);
			map.put("authorToken", blogDto.getAuthorToken());

			loggingService.createLog("NullPointerException", new Timestamp(System.currentTimeMillis()), false, 404,
					"AuthorToken must not be null");

			log.info("log INFO {}", "AuthorToken must not be null");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		UserDetail user = userDetailRepository.findByTokenuserAndActive(blogDto.getAuthorToken(), true);
		if (null == user) {
			map.put("code", 404);
			map.put("message", "Author not found");
			map.put("status", false);
			map.put("Author", blogDto.getAuthorToken());

			loggingService.createLog("NoSuchElementException", new Timestamp(System.currentTimeMillis()), false, 404,
					"Author not found");

			log.info("log INFO {}", "Author not found");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		if ((blogDto.getCoverImageToken()).isEmpty()) {
			map.put("code", 404);
			map.put("message", "CoverImage Required");
			map.put("Image Token", blogDto.getCoverImageToken());
			map.put("status", false);

			loggingService.createLog("NullPointerException", new Timestamp(System.currentTimeMillis()), false, 404,
					"CoverImage must not be null");

			log.info("log INFO {}", "CoverImage must not be null");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}

		blogCoverImage = coverImageRepository.findByImagetoken(blogDto.getCoverImageToken());
		if (null == blogCoverImage) {
			map.put("code", 404);
			map.put("message", "CoverImage not found");
			map.put("status", false);
			map.put("title", blogDto.getCoverImageToken());

			loggingService.createLog("NoSuchElementException", new Timestamp(System.currentTimeMillis()), false, 404,
					"CoverImage not found");

			log.info("log INFO {}", "CoverImage not found");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);

		} else {
			// BlogCoverImage
			// bi=coverImageRepository.existsByImagetoken(blogDto.getCoverImageToken());
			if (blogCoverImage.getBlogtoken() != null) {
				map.put("code", 404);
				map.put("message", "CoverImage is already used by other Blog");
				map.put("status", false);
				map.put("title", blogDto.getCoverImageToken());

				loggingService.createLog("_", new Timestamp(System.currentTimeMillis()), false, 404,
						"CoverImage must not be duplicate");

				log.info("log INFO {}", "CoverImage must not be duplicate");
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
			} else {
				blogCoverImage.setBlogtoken(token);
			}
		}
		if (blogDto.getFilesToken().length == 0) {

		} else {
			for (int i = 0; i < blogDto.getFilesToken().length; i++) {
				dbFile = dBFileRepository.findByToken(blogDto.getFilesToken()[i]);
				if (null == dbFile) {
					map.put("code", 404);
					map.put("message", "File not found");
					map.put("status", false);
					map.put("title", blogDto.getFilesToken()[i]);

					loggingService.createLog("NoSuchElementException", new Timestamp(System.currentTimeMillis()), false,
							404, "File not found");

					log.info("log INFO {}", "File not found");
					return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
				} else {
					DBFile db = new DBFile(dbFile, blogDto.getToken());
					dBFileRepository.save(db);
				}
			}
		}
		blogServiceImpl.createBlog(blogDto);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("token", blogDto.getToken());
		map1.put("title", blogDto.getTitle());
		map1.put("content", blogDto.getContent());
		map1.put("hashTags", blogDto.getHashTags());
		map1.put("authorToken", blogDto.getAuthorToken());
		map1.put("authorName", user.getUsername());
		map1.put("createdTime", blogDto.getCreatedTime());
		map1.put("coverImage", blogCoverImage.getDownloadUrl());
		map.put("data", map1);
		map.put("code", 200);

		map.put("message", "Blog created Successfully");
		map.put("status", true);
		loggingService.createLog("_", new Timestamp(System.currentTimeMillis()), true, 200,
				"Blog created successfully");
		log.info("log INFO {}", "Blog created successfully");
		return ResponseEntity.ok(map);
	}

	@PutMapping("/updateBlog")
	public ResponseEntity<?> updateBlog(@RequestBody BlogDto blogDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> maps = new LinkedHashMap<String, Object>();
		if (blogDto.getToken().isEmpty()) {
			map.put("code", 404);
			map.put("message", "Token Required.");
			map.put("status", false);
			map.put("Token", blogDto.getToken());

			loggingService.createLog("NullPointerException", new Timestamp(System.currentTimeMillis()), false, 404,
					"BlogToken must not be null");

			log.info("log INFO {}", "BlogToken must not be null");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		if (blogDto.getAuthorToken().isEmpty()) {
			map.put("code", 404);
			map.put("message", "authorToken Required.");
			map.put("status", false);
			map.put("authorToken", blogDto.getAuthorToken());

			loggingService.createLog("NullPointerException", new Timestamp(System.currentTimeMillis()), false, 404,
					"AuthorToken must not be null");

			log.info("log INFO {}", "AuthorToken must not be null");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		if (!(blogRepository.existsByToken(blogDto.getToken()))) {
			map.put("code", 404);
			map.put("token", blogDto.getToken());
			map.put("message", "Blog not Found");
			map.put("status", false);

			loggingService.createLog("NoSuchElementException", new Timestamp(System.currentTimeMillis()), false, 404,
					"Blog not found");

			log.info("log INFO {}", "Blog not found");
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}

		else {

			Blog blog = blogRepository.findByToken(blogDto.getToken());
			String authorToken1 = blog.getAuthorToken();

			blogDto.setToken(blog.getToken());
			blogDto.setCreatedTime(blog.getCreatedTime());

			if (blogDto.getAuthorToken().equals(authorToken1)) {
				if (blogDto.getTitle().isEmpty()) {
					blogDto.setTitle(blog.getTitle());
				}
				if (blogDto.getContent().isEmpty()) {
					blogDto.setContent(blog.getContent());
				}
				if (blogDto.getHashTags().isEmpty()) {
					blogDto.setHashTags(blog.getHashTags());
				}

				if ((blogDto.getCoverImageToken()).isEmpty()) {
					blogCoverImage = coverImageRepository.findByBlogtoken(blogDto.getToken());
					blogDto.setCoverImageToken(blogCoverImage.getImagetoken());
				}

				else {
					blogCoverImage = coverImageRepository.findByImagetoken(blogDto.getCoverImageToken());
					if (null == blogCoverImage) {
						map.put("code", 404);
						map.put("message", "CoverImage not found");
						map.put("status", false);
						map.put("ImageToken", blogDto.getCoverImageToken());

						loggingService.createLog("NoSuchElementException", new Timestamp(System.currentTimeMillis()),
								false, 404, "CoverImage not found");

						log.info("log INFO {}", "CoverImage not found");
						return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
					} else {
						if (blogCoverImage.getBlogtoken() == null
								|| blogCoverImage.getBlogtoken().equals(blogDto.getToken()))// ||(blogCoverImage.getBlogtoken().equals(blogDto.getToken())))
						{
							BlogCoverImage bi = coverImageRepository.findByBlogtoken(blogDto.getToken());
							if (bi == null) {
								blogCoverImage.setBlogtoken(blogDto.getToken());
							} else {
								coverImageRepository.deleteByBlogtoken(bi.getBlogtoken());
								blogCoverImage.setBlogtoken(blogDto.getToken());
							}

						} else {
							map.put("code", 404);
							map.put("message", "CoverImage is already used by other Blog");
							map.put("status", false);
							map.put("title", blogDto.getCoverImageToken());

							loggingService.createLog("_", new Timestamp(System.currentTimeMillis()), false, 404,
									"CoverImage must not be duplicate");

							log.info("log INFO {}", "CoverImage must not be duplicate");
							return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
						}
					}
				}
				if (blogDto.getFilesToken().length == 0) {

				} else {
					for (int i = 0; i < blogDto.getFilesToken().length; i++) {
						dbFile = dBFileRepository.findByToken(blogDto.getFilesToken()[i]);
						if (null == dbFile) {
							map.put("code", 404);
							map.put("message", "File not found");
							map.put("status", false);
							map.put("title", blogDto.getFilesToken()[i]);

							loggingService.createLog("NoSuchElementException",
									new Timestamp(System.currentTimeMillis()), false, 404, "File not found");

							log.info("log INFO {}", "File not found");
							return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
						} else {
							DBFile db = new DBFile(dbFile, blogDto.getToken());
							dBFileRepository.save(db);
						}
					}
				}
				blogDto.setAuthorToken(blog.getAuthorToken());
				blogServiceImpl.updateBlog(blogDto);
				map.put("data", blogDto);
				maps.put("data", map);
				map.put("message", "Blog Updated Successfully");
				map.put("status", true);
				map.put("code", 200);
				loggingService.createLog("_", new Timestamp(System.currentTimeMillis()), true, 200,
						"Blog Updated Successfully");
				log.info("log INFO {}", "Blog Updated Successfully");
				ResponseEntity.ok(maps);
			} else {
				map.put("code", 404);
				map.put("authorToken", blogDto.getAuthorToken());
				map.put("message", "Author not Found");
				map.put("status", false);

				loggingService.createLog("NoSuchElementException", new Timestamp(System.currentTimeMillis()), false,
						404, "Author not found");

				log.info("log INFO {}", "Author not found");
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
			}
		}
		return ResponseEntity.ok(map);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteBlog(@RequestParam("token") String token,
			@RequestParam("authorToken") String authorToken) {
		return blogServiceImpl.deleteFile(authorToken, token);
	}

	@GetMapping("/getAllBlogs")
	public ResponseEntity<?> getAllBlogs() {
		return blogService.getAllBlog();
	}

	@GetMapping("/view-post/{token}")
	public ResponseEntity<?> viewPost(@PathVariable String token) {
		return blogService.viewPost(token);
	}
}