package com.JWTSptingWebApp.jeniluserdetail.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Response {

	public HashMap<String,Object> addMap(LinkedHashMap<String, Object> addmap){
		LinkedHashMap<String , Object> datamap = new LinkedHashMap<String , Object>(); 
		datamap.put("code", 200);
		datamap.put("status", true);
		datamap.put("message", "User Created");
		datamap.put("data", addmap);
		return datamap;
	}

	public HashMap<String,Object> getUseraddMap(List<JenilUserDetail> listJenilUser){
		LinkedHashMap<String , Object> datamap = new LinkedHashMap<String , Object>(); 
		datamap.put("code", 200);
		datamap.put("status", true);
		datamap.put("message", "GetAll User");
		datamap.put("data", listJenilUser);
		return datamap;
	}
	
	public HashMap<String,Object> deleteUseraddMap(String usertoken){
		LinkedHashMap<String , Object> datamap = new LinkedHashMap<String , Object>(); 
		datamap.put("code", 200);
		datamap.put("status", true);
		datamap.put("message", "Delete User and If there was a blog available Then blog has been also Deleted");
		datamap.put("usertoken", usertoken);
		return datamap;
	}
	
	public HashMap<String,Object> updateUseraddMap(JenilUserDetail jenilUserDetail){
		LinkedHashMap<String , Object> datamap = new LinkedHashMap<String , Object>(); 
		datamap.put("code", 200);
		datamap.put("status", true);
		datamap.put("message", "Update UserDetail");
		datamap.put("data" , jenilUserDetail);
		return datamap;
	}
}
