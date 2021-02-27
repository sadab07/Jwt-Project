
package com.JWTSptingWebApp.BlogMng.Model;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "Blog")
public class Blog {
	@Id
	private String token;
	private String title;
	private String content;
	@Column(name = "hashtags")
	private String hashTags;
	//private String author;
	@Column(name = "authortoken")
	private String authorToken;
	@Column(name = "createdtime")
	private String createdTime;
	
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String string) {
		this.createdTime = string;
	}
	public String getAuthorToken() {
		return authorToken;
	}
	public void setAuthorToken(String authorToken) {
		this.authorToken = authorToken;
	}

	/*
	 * public String getAuthor() { return author; } public void setAuthor(String
	 * author) { this.author = author; }
	 */
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
	public Blog(String token, String title, String content, String hashTags,String authorToken,String createdTime) {
		this.token = token;
		this.title = title;
		this.content = content;
		this.hashTags = hashTags;
		//this.author=author;
		this.authorToken=authorToken;
		this.createdTime=createdTime;
	}
	public Blog() {
		
	}
	
	
		
}
