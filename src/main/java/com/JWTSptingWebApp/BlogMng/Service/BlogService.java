
package com.JWTSptingWebApp.BlogMng.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.JWTSptingWebApp.BlogMng.Dto.BlogDto;

import com.JWTSptingWebApp.BlogMng.Model.Blog;
import com.JWTSptingWebApp.BlogMng.Model.BlogCoverImage;



public interface BlogService {

	public void updateBlog(BlogDto blogDto);
	
	public  Blog createBlog(BlogDto blogDto);
	
	public Blog convertDtoToModelBlog(BlogDto blogDto);
	
	public BlogDto convertModelToDtoBlog(Blog blog);
	
	public ResponseEntity<?> deleteFile(String authortoken,String token);
	
	public ResponseEntity<?> getAllBlog();
	
	public ResponseEntity<?> viewPost(String token);
	
	public BlogCoverImage storeimage(MultipartFile file) ;
	
	public BlogCoverImage getFile(String fileToken);
	
}