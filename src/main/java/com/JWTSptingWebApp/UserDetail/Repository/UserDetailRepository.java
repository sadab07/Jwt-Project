
package com.JWTSptingWebApp.UserDetail.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.JWTSptingWebApp.UserDetail.model.UserDetail;

@Repository

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {


	
	public UserDetail findByEmailAndActive(String email,boolean b);

	public UserDetail findByUsernameAndActive(String username,boolean b);

	public UserDetail findByUseridAndActive(Long userid,boolean b);

	
	
	public UserDetail findByTokenuserAndActive(String token,boolean b);
 
	boolean existsByTokenuserAndActive(String token,boolean b);

	public void deleteByTokenuserAndActive(String token,boolean b);

	public List<UserDetail> findAllByTokenuser(String token);

	@Query(value = "select tokenuser from user_detail where tokenuser= :user", nativeQuery = true)
	public String findByUserToke(@Param("user") String username);

	@Query(value = "select tokenuser from user_detail where tokenuser= :user", nativeQuery = true)
	public String findByUserToken(@Param("user") String username);

	@Query(value = "select * from user_detail", nativeQuery = true)
	public String[] getUsernames();

	@Query(value = "select tokenuser from user_detail", nativeQuery = true)
	public String[] getTokens();

}
