package com.JWTSptingWebApp.springbootsecurity.config;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.JWTSptingWebApp.UserDetail.Repository.UserDetailRepository;
import com.JWTSptingWebApp.UserDetail.model.UserDetail;
import com.JWTSptingWebApp.springbootsecurity.model.UserDTO;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDetailRepository userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	public UserDetailRepository userDetailRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> roles = null;

		UserDetail user = userDao.findByUsernameAndActive(username, true);
		if (user != null) {
			roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
			return new User(user.getUsername(), user.getPassword(), roles);
		}
		throw new UsernameNotFoundException("User not found with the name " + username);
	}

	public UserDetail save(UserDTO user) {
		UserDetail newUser = new UserDetail();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setRole((user.getRole() != null)?user.getRole():"ROLE_USER");
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstname());
		newUser.setLastName(user.getLastname());
		newUser.setEnabled(true);
		return userDao.save(newUser);
	}

}
