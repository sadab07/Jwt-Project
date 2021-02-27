package com.JWTSptingWebApp.jeniluserdetail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.JWTSptingWebApp.jeniluserdetail.model.JenilUserDetail;


@Repository
public interface JenilUserDetailRepository extends JpaRepository<JenilUserDetail, String>{

	
	public JenilUserDetail findByEmail(String email);

	public JenilUserDetail findByUsername(String username);

	public JenilUserDetail findByUsertoken(String usertoken);
	
	public boolean existsByUsertoken(String usertoken);

	@Query(value = "select * from jenil_user_detail where active= 1",nativeQuery = true)
	public List<JenilUserDetail> findByActive(boolean active);



}
