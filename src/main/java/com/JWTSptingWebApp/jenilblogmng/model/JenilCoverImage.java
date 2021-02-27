package com.JWTSptingWebApp.jenilblogmng.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class JenilCoverImage {

	@Id
	private String imagetoken;
	private String blogtoken;
	private String imagename;
	private String imagepath;
	private boolean active;
	private String downloadurl;
	
	
	public String getDownloadurl() {
		return downloadurl;
	}
	public void setDownloadurl(String downloadurl) {
		this.downloadurl = downloadurl;
	}
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getImagetoken() {
		return imagetoken;
	}
	public void setImagetoken(String imagetoken) {
		this.imagetoken = imagetoken;
	}
	public String getBlogtoken() {
		return blogtoken;
	}
	public void setBlogtoken(String blogtoken) {
		this.blogtoken = blogtoken;
	}
	public String getImagename() {
		return imagename;
	}
	public void setImagename(String imagename) {
		this.imagename = imagename;
	}
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	
}
