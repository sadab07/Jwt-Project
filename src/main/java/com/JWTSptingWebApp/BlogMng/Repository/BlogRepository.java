
package com.JWTSptingWebApp.BlogMng.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.JWTSptingWebApp.BlogMng.Model.Blog;

import com.JWTSptingWebApp.singlefileupload.model.DBFile;

public interface BlogRepository extends JpaRepository<Blog, String>{

	Blog findByToken(String token);
	
	 boolean existsByToken(String token);

		@Query(value = "SELECT token FROM blog WHERE authortoken=? and token=?", nativeQuery = true)
		public String authortoken(String authortoken,String token);

		
		@Transactional
		@Modifying
		@Query(value = "delete FROM blog WHERE authortoken=? and token=?",nativeQuery = true)
	 	public void deleteblogbytoken(String authortoken,String token);

		Blog findByAuthorToken(String authorToken);

}