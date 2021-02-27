package com.JWTSptingWebApp.jenilsinglefile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.JWTSptingWebApp.jenilsinglefile.model.JenilFileDetail;

@Repository
public interface JenilFileRepository extends JpaRepository<JenilFileDetail, String>{

	@Query(value = "select * from jenil_file_detail where filetoken= ?",nativeQuery = true)
	Optional<JenilFileDetail> findByToken(String filetoken);
	
	@Query(value = "select * from jenil_file_detail where active= 1",nativeQuery = true)
	List<JenilFileDetail> findByActive(boolean active);

	JenilFileDetail findByFiletoken(String filetoken);

	List<JenilFileDetail> findByBlogtoken(String blogtoken);

	
	

}
