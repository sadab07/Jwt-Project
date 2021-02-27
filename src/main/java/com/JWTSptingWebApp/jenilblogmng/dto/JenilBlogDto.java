package com.JWTSptingWebApp.jenilblogmng.dto;

public class JenilBlogDto {

	private String blogtoken;
	private String authortoken;
	private String blogtitle;
	private String blogcontent;
	private String hashtags;
	private String createdtime;
	private String authorname;
	private String[] filetoken;
	private String imagetoken;
	private boolean active;
	
	
	public String getImagetoken() {
		return imagetoken;
	}
	public void setImagetoken(String imagetoken) {
		this.imagetoken = imagetoken;
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
	public String getAuthorname() {
		return authorname;
	}
	public void setAuthorname(String authorname) {
		this.authorname = authorname;
	}
	public String[] getFiletoken() {
		return filetoken;
	}
	public void setFiletoken(String[] filetoken) {
		this.filetoken = filetoken;
	}
	
	
	
}
