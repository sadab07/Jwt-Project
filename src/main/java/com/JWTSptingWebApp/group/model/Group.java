package com.JWTSptingWebApp.group.model;



import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
@Entity
@Table(name = "group_tabl")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 5)
	private int gid;

	@Column(length = 48,nullable = false,unique = true)
	
	private String groupname;
	@Column(length = 24,nullable = false)
	
	private String grouptype;
	
	@Column(length = 16,nullable = false,unique = true)
	private String token;
	
//	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//	@JoinColumn(name = "gr_id",referencedColumnName = "gid")
//	private List<User> user=new ArrayList<User>();
//
//	public List<User> getUser() {
//		return user;
//	}
//	public void setUser(List<User> user) {
//		this.user = user;
//	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getGrouptype() {
		return grouptype;
	}
	public void setGrouptype(String grouptype) {
		this.grouptype = grouptype;
	}
	
}
