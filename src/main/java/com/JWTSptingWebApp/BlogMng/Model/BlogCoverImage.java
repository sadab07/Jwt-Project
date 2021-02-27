package com.JWTSptingWebApp.BlogMng.Model;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "Coverimage")
public class BlogCoverImage {

	@Id

	private String imagetoken;

	private String imagename;

	private String imagetype;
	private String downloadurl;
	
	@Lob
	private byte[] data;
	
	@Override
	public String toString() {
		return "BlogCoverImage [imagetoken=" + imagetoken + ", imageName=" + imagename + ", imageType=" + imagetype
				+ ", downloadUrl=" + downloadurl + ", data=" + Arrays.toString(data) + ", blogtoken=" + blogtoken + "]";
	}

	private String blogtoken;

	public BlogCoverImage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BlogCoverImage(String imagetoken, String imageName, String imageType, String downloadUrl, byte[] data,
			String blogtoken) {
		super();
		this.imagetoken = imagetoken;
		this.imagename = imageName;
		this.imagetype = imageType;
		this.downloadurl = downloadUrl;
		this.data = data;
		this.blogtoken = blogtoken;
	}
	
	public BlogCoverImage(BlogCoverImage bg,String blogtoken)
	{
		
		this.imagetoken=bg.getImagetoken();
		this.imagename=bg.getImageName();
		this.imagetype=bg.getImageType();
		this.downloadurl=bg.getDownloadUrl();
		this.data=bg.getData();
		this.blogtoken=blogtoken;
	}
	public BlogCoverImage(String generatedToken, String imageName, String imageType, byte[] data,
			String downloadUrl) {
		this.imagetoken = generatedToken;
		this.imagename = imageName;
		this.imagetype = imageType;
		this.data = data;
		this.downloadurl = downloadUrl;
	}

	public String getImagetoken() {
		return imagetoken;
	}

	public void setImagetoken(String imagetoken) {
		this.imagetoken = imagetoken;
	}

	public String getImageName() {
		return imagename;
	}

	public void setImageName(String imageName) {
		this.imagename = imageName;
	}

	public String getImageType() {
		return imagetype;
	}

	public void setImageType(String imageType) {
		this.imagetype = imageType;
	}

	public String getDownloadUrl() {
		return downloadurl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadurl = downloadUrl;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getBlogtoken() {
		return blogtoken;
	}

	public void setBlogtoken(String blogtoken) {
		this.blogtoken = blogtoken;
	}
	
	
}
