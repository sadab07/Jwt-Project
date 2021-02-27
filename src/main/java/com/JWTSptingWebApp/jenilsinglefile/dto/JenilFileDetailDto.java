package com.JWTSptingWebApp.jenilsinglefile.dto;

public class JenilFileDetailDto {

	private String filetoken;
	private String filename;
	private String filetype;
	private String filepath;
	private String downloadurl;
	private boolean active;
	private String blogtoken;
	
	
	public String getBlogtoken() {
		return blogtoken;
	}
	public void setBlogtoken(String blogtoken) {
		this.blogtoken = blogtoken;
	}
	public String getFiletoken() {
		return filetoken;
	}
	public void setFiletoken(String filetoken) {
		this.filetoken = filetoken;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
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
	
}
