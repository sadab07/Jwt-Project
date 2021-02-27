package com.JWTSptingWebApp.jenilblogmng.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JWTSptingWebApp.jenilblogmng.model.JenilCoverImage;

@Repository
public interface JenilCoverImageRepository extends JpaRepository<JenilCoverImage, String>{

	Optional<JenilCoverImage> findByImagetoken(String imagetoken);

	JenilCoverImage findByBlogtoken(String blogtoken);

	

}
