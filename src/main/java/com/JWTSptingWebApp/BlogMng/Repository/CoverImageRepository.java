package com.JWTSptingWebApp.BlogMng.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JWTSptingWebApp.BlogMng.Model.BlogCoverImage;

public interface CoverImageRepository extends JpaRepository<BlogCoverImage, String>{

	BlogCoverImage findByImagetoken(String generatedToken);

	void deleteByBlogtoken(String token);
	

	BlogCoverImage findByBlogtoken(String blogToken);

	BlogCoverImage existsByImagetoken(String coverImageToken);	

}