
package com.JWTSptingWebApp.singlefileupload.model;

import javax.persistence.*;


import org.springframework.stereotype.Service;

import com.JWTSptingWebApp.MettingMng.Model.Metting;

@Entity
@Table(name = "files")
@Service
public class DBFile {

	@Id

	private String token;

	private String fileName;

	private String fileType;
	private String downloadUrl;
	
	@Lob
	private byte[] data;
	@ManyToOne(cascade = CascadeType.REMOVE)
	Metting metting;
	private String blogtoken;




	public String getBlogtoken() {
		return blogtoken;
	}

	public void setBlogtoken(String blogtoken) {
		this.blogtoken = blogtoken;
	}

	public DBFile(DBFile db,String blogtoken) {
		this.token=db.getToken();
		this.fileName=db.getFileName();
		this.fileType=db.getFileType();
		this.downloadUrl=db.getDownloadUrl();
		this.data=db.getData();
		this.blogtoken = blogtoken;
	}

	public DBFile() {
		
	}

	public DBFile(String generatedToken, String fileDownloadUrl, String updatedName, String contentType, byte[] bytes,Metting meeting) {
		this.token = generatedToken;
		this.fileName = updatedName;
		this.fileType = contentType;
		this.data = bytes;
		this.downloadUrl = fileDownloadUrl;
		this.metting=meeting;
	}

	public DBFile(String generatedToken, String updatedName, String contentType, byte[] bytes, String fileDownloadUri) {
		this.token = generatedToken;
		this.fileName = updatedName;
		this.fileType = contentType;
		this.data = bytes;
		this.downloadUrl = fileDownloadUri;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public Metting getMetting() {
		return metting;
	}

	public void setMetting(Metting metting) {
		this.metting = metting;
	}

	

}
