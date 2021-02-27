
package com.JWTSptingWebApp.singlefileupload.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.JWTSptingWebApp.BlogMng.Model.Blog;
import com.JWTSptingWebApp.singlefileupload.model.DBFile;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {
	
	 @Transactional
		@Modifying
	@Query(value = "select * from files f where f.blogtoken = :blogtoken",nativeQuery = true)
	List<DBFile> findByblogToken(String blogtoken);
	
	DBFile findByToken(String token);

	void deleteByToken(String token);


	DBFile existsByToken(String filetoken);
	Blog findByBlogtoken(String token);
	
	

	 List<DBFile> findByMetting_token(String token); 
	 
	 @Transactional
		@Modifying
		@Query(value = "delete from files b WHERE b.blogtoken= :blogtoken",nativeQuery = true)
		public void deletefile(String blogtoken);
	 
	 
}

