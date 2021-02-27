package com.JWTSptingWebApp.BlogMng.Dto;

import java.sql.Timestamp;




public class BlogDto {

	private String token;
	private String title;
	private String content;
	private String hashTags;
	
	private String authorToken;
	private String createdTime;
	private String coverImageToken;
	private String[] filesToken;
	
	public String getCoverImageToken() {
		return coverImageToken;
	}	public void setCoverImageToken(String coverImageToken) {
		this.coverImageToken = coverImageToken;
	}
	public String[] getFilesToken() {
		return filesToken;
	}
	public void setFilesToken(String[] filesToken) {
		this.filesToken = filesToken;
	}
	
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHashTags() {
		return hashTags;
	}
	public void setHashTags(String hashTags) {
		this.hashTags = hashTags;
	}
	public BlogDto() {
		
	}
	
	public BlogDto(String token, String title, String content, String hashTags, String authorToken,
			String createdTime, String coverImageToken, String[] filesToken) {
		super();
		this.token = token;
		this.title = title;
		this.content = content;
		this.hashTags = hashTags;
		
		this.authorToken = authorToken;
		this.createdTime = createdTime;
		this.coverImageToken = coverImageToken;
		this.filesToken = filesToken;
	}
	public String getAuthorToken() {
		return authorToken;
	}
	public void setAuthorToken(String authorToken) {
		this.authorToken = authorToken;
	}
	
	
}
