package com.JWTSptingWebApp.group.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.JWTSptingWebApp.group.model.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {

//	@Query(value = "SELECT group_table.groupname,group_table.grouptype,user_table.username FROM group_table INNER JOIN user_table ON group_table.gid=user_table.group_gid",nativeQuery = true)
//	List<String> getAll();
	
//	@Query(value = "SELECT g.groupname,g.grouptype,u.username,u.token FROM user_tabl u LEFT JOIN group_tabl g ON u.group_gid=g.gid",nativeQuery = true)
//	List<String> getAll1();
//	@Query(value = "select uid,username,token from user_tabl where group_gid= :n",nativeQuery = true)
//	List<User> getUser(@Param("n") int grid);
	@Query(value = "SELECT groupname FROM group_tabl WHERE groupname= ?",nativeQuery = true)
	public String getGroupName(String grpname);
	
	@Query(value = "SELECT groupname FROM group_tabl WHERE token= ?",nativeQuery = true)
	public String getGroupNamebytoken(String token);
	
	
	public Optional<Group> findByToken(String token);
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM group_tabl WHERE token= :tokent",nativeQuery = true)
	public void deleteebytoken(@Param("tokent") String token);

}
