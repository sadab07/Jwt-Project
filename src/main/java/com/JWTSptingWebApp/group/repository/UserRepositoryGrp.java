package com.JWTSptingWebApp.group.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.JWTSptingWebApp.group.model.User;


public interface UserRepositoryGrp extends JpaRepository<User, Integer> {
	@Query(value = "select username from user_tabl where group_gid= :n",nativeQuery = true)
	String[] getUser(@Param("n") int i);
	
	public Optional<User> findByToken(String token);
	
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM user_tabl WHERE group_gid= :grpid",nativeQuery = true)
	public void deleteebygid(@Param("grpid") int gid);
	
}
