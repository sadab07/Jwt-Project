package com.JWTSptingWebApp.MettingMng.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JWTSptingWebApp.MettingMng.Model.Metting;

@Repository
public interface MettingRepository extends JpaRepository<Metting, String>{

	public Metting findByToken(String token);

	public  boolean existsByToken(String token);
		

}
