package com.JWTSptingWebApp.springbootsecurity.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "apilinkdetail")
public class ApiLinkDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "apiid", nullable = false, updatable = false)
	private long apiid;
	
	@Column(name = "linkid", nullable = false, updatable = false)
	private long linkid;
	
	private String linkname;
	
	private String role;
	
	private int active;

	public long getApiid() {
		return apiid;
	}

	public void setApiid(long apiid) {
		this.apiid = apiid;
	}

	public long getLinkid() {
		return linkid;
	}

	public void setLinkid(long linkid) {
		this.linkid = linkid;
	}

	public String getLinkname() {
		return linkname;
	}

	public void setLinkname(String linkname) {
		this.linkname = linkname;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
