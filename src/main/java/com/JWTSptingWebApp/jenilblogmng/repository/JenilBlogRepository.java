package com.JWTSptingWebApp.jenilblogmng.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.JWTSptingWebApp.jenilblogmng.model.JenilBlog;

@Repository
public interface JenilBlogRepository extends JpaRepository<JenilBlog, String>{

	@Query(value = "select * from jenil_blog where blogtoken= ?",nativeQuery = true)
	JenilBlog findByToken(String blogtoken);
	
	@Query(value = "select * from jenil_blog where active= 1",nativeQuery = true)
	List<JenilBlog> findByActive(boolean active);

	JenilBlog findByAuthortoken(String authortoken);

}
