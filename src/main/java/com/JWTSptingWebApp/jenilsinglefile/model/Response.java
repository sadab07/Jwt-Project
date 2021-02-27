package com.JWTSptingWebApp.jenilsinglefile.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Response {

	public static void main(String args[]) {
		
	}
	public LinkedHashMap<String, Object> addMap(LinkedHashMap<String,Object> addMap){
		LinkedHashMap<String, Object> codemap = new LinkedHashMap<String , Object>();
		codemap.put("code", 200);
		codemap.put("status", true);
		codemap.put("message", "FileUpload Successfully!!");
		codemap.put("data", addMap);
		return codemap;
	}
	public LinkedHashMap<String, Object> addMapMultiFiles(List<Map<String, Object>> list) {
		LinkedHashMap<String, Object> codemap = new LinkedHashMap<String , Object>();
		codemap.put("code", 200);
		codemap.put("status", true);
		codemap.put("message", "All Files Upload Successfully!!");
		codemap.put("data", list);
		return codemap;
	}
	public LinkedHashMap<String, Object> addMapDeleteFile(String filetoken) {
		LinkedHashMap<String, Object> codemap = new LinkedHashMap<String , Object>();
		codemap.put("code", 200);
		codemap.put("status", true);
		codemap.put("message", "Delete File Successfully!!");
		codemap.put("filetoken", filetoken);
		return codemap;
	}
	public LinkedHashMap<String, Object> addMapGetFiles(List<JenilFileDetail> detail) {
		LinkedHashMap<String, Object> codemap = new LinkedHashMap<String , Object>();
		codemap.put("code", 200);
		codemap.put("status", true);
		codemap.put("message", "Get All Files");
		codemap.put("filetoken", detail);
		return codemap;
	}
}
