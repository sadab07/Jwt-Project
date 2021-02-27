package com.JWTSptingWebApp.jenilblogmng.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class JenilBlog {
	
	@Id
	private String blogtoken;
	private String authortoken;
	private String blogtitle;
	private String blogcontent;
	private String hashtags;
	private String createdtime;
	@JsonIgnore
	private boolean active;
	
	
	public JenilBlog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JenilBlog(String blogtoken, String authortoken, String blogtitle, String blogcontent, String hashtags,
			String createdtime, boolean active) {
		super();
		this.blogtoken = blogtoken;
		this.authortoken = authortoken;
		this.blogtitle = blogtitle;
		this.blogcontent = blogcontent;
		this.hashtags = hashtags;
		this.createdtime = createdtime;
		this.active = active;
	}
	@Override
	public String toString() {
		return "JenilBlog [blogtoken=" + blogtoken + ", authortoken=" + authortoken + ", blogtitle=" + blogtitle
				+ ", blogcontent=" + blogcontent + ", hashtags=" + hashtags + ", createdtime=" + createdtime
				+ ", active=" + active + "]";
	}
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getBlogtoken() {
		return blogtoken;
	}
	public void setBlogtoken(String blogtoken) {
		this.blogtoken = blogtoken;
	}
	
	public String getAuthortoken() {
		return authortoken;
	}
	public void setAuthortoken(String authortoken) {
		this.authortoken = authortoken;
	}
	public String getBlogtitle() {
		return blogtitle;
	}
	public void setBlogtitle(String blogtitle) {
		this.blogtitle = blogtitle;
	}
	public String getBlogcontent() {
		return blogcontent;
	}
	public void setBlogcontent(String blogcontent) {
		this.blogcontent = blogcontent;
	}
	public String getHashtags() {
		return hashtags;
	}
	public void setHashtags(String hashtags) {
		this.hashtags = hashtags;
	}
	public String getCreatedtime() {
		return createdtime;
	}
	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
	}
	
	
	
}
