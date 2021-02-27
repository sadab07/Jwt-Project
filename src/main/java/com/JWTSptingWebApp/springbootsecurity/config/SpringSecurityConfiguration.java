
package com.JWTSptingWebApp.springbootsecurity.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.JWTSptingWebApp.springbootsecurity.model.ApiLinkDetail;
import com.JWTSptingWebApp.springbootsecurity.repository.ApiLinkDetailRepository;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private ApiLinkDetailRepository apiLinkDetailRep;
	
	@Autowired
	private CustomJwtAuthenticationFilter customJwtAuthenticationFilter;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		List<String> apiListForUser= new ArrayList<String>();
		List<String> apiListForAdmin= new ArrayList<String>();
		List<ApiLinkDetail> apiLinkList= new ArrayList<>();
		apiLinkList = apiLinkDetailRep.findAll();
		
		if(null != apiLinkList && !apiLinkList.isEmpty()) {
			for(int i = 0;i<apiLinkList.size();i++) {
				ApiLinkDetail apiLinkDet = new ApiLinkDetail();
				apiLinkDet = apiLinkList.get(i);
				if(null != apiLinkDet) {
					String linkname = apiLinkDet.getLinkname();
					String role = apiLinkDet.getRole();
					Integer active = apiLinkDet.getActive();
					
					if(null != linkname && active == 1 && role.equalsIgnoreCase("USER")) {
						apiListForUser.add(linkname);
					}else if(null != linkname && active == 1 && role.equalsIgnoreCase("ADMIN")) {
						apiListForAdmin.add(linkname);
					}
				}
			}
		}
		
		System.err.println(apiListForUser.toString());

		http.csrf().disable()
		.authorizeRequests()
		//.antMatchers("/helloadmin").hasRole("ADMIN")
		//.antMatchers("/hellouser").hasAnyRole("USER","ADMIN")
		.antMatchers(apiListForUser.toString()).hasAnyRole("USER","ADMIN")
		.antMatchers(apiListForAdmin.toString()).hasRole("ADMIN")
		//.antMatchers("/user/listAll").hasRole("ADMIN")
		//.antMatchers(apiList.stream().collect(Collectors.joining(","))).permitAll().anyRequest().authenticated()
		//.antMatchers(apiList.stream().collect(Collectors.joining(","))).hasRole("USER")

		.antMatchers("/login", "/forgot-password","/verify-otp","/change-password","/user/create","/taskDetails/**","/blogImage/downloadCoverImage/{token}","/file/**","/swagger-ui.html/**","/v2/api-docs").permitAll().anyRequest().authenticated()

		.and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).
		and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
		and().addFilterBefore(customJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
	}



}