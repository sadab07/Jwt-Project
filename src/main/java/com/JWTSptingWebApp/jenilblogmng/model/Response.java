package com.JWTSptingWebApp.jenilblogmng.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Response {

	public static void main(String args[]) {
		
	}
	
	public LinkedHashMap<String, Object> addMap(LinkedHashMap<String, Object> map){
		LinkedHashMap<String, Object> outmap = new LinkedHashMap<String, Object>();
		outmap.put("code", 200);
		outmap.put("status", true);
		outmap.put("message", "Blog Created successfully");
		outmap.put("data", map);
		return outmap;
	}
	
	public HashMap<String,Object> deleteMap(String blogtoken){
		LinkedHashMap<String , Object> datamap = new LinkedHashMap<String , Object>(); 
		datamap.put("code", 200);
		datamap.put("status", true);
		datamap.put("message", "Delete Blog successfully");
		datamap.put("blogtoken", blogtoken);
		return datamap;
	}
	
	public LinkedHashMap<String, Object> addMapGetBlogs(List<JenilBlog> blogs) {
		LinkedHashMap<String, Object> codemap = new LinkedHashMap<String , Object>();
		codemap.put("code", 200);
		codemap.put("status", true);
		codemap.put("message", "AllBlogs have been shown successfully");
		codemap.put("data", blogs);
		return codemap;
	}
	
	public LinkedHashMap<String, Object> updateBlogaddMap(LinkedHashMap<String, Object> map){
		LinkedHashMap<String, Object> outmap = new LinkedHashMap<String, Object>();
		outmap.put("code", 200);
		outmap.put("status", true);
		outmap.put("message", "Blog Update successfully");
		outmap.put("data", map);
		return outmap;
	}
	
	public LinkedHashMap<String, Object> viewpostBlogaddMap(LinkedHashMap<String,Object> map){
		LinkedHashMap<String, Object> outmap = new LinkedHashMap<String, Object>();
		outmap.put("code", 200);
		outmap.put("status", true);
		outmap.put("message", "View-Post Successfully");
		outmap.put("data", map);
		return outmap;
	}
}
