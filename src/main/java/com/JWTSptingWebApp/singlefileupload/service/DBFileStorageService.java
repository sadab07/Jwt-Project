
package com.JWTSptingWebApp.singlefileupload.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.JWTSptingWebApp.MettingMng.Model.Metting;
import com.JWTSptingWebApp.singlefileupload.exception.FileStorageException;

import com.JWTSptingWebApp.singlefileupload.model.DBFile;
import com.JWTSptingWebApp.singlefileupload.repository.DBFileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.servlet.http.HttpSession;

@Service
public class DBFileStorageService {
	
	private static String LOCAL_DIRECTORY = "C://FILESTORE//";
	@Autowired
	private DBFileRepository dbFileRepository;
	

	public DBFile storeFile(MultipartFile file) {

		String uuid = UUID.randomUUID().toString();
		String uid = uuid.toString().replaceFirst("[a-z]", uuid.toString().toUpperCase()).replace("-", "")
				.replaceFirst("[a-z]", uuid.toString().toLowerCase());
		String generatedToken = uid.substring(29, 37);

		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String dateTime = now.format(myFormatObj).replace(" ", "_").replace(":", "_");
		
		String updatedName = generatedToken + "_" + dateTime;
		try {

			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			
			String originalFileName = file.getOriginalFilename();

			String extension = "";

			int i = originalFileName.lastIndexOf('.');
			if (i > 0) {
				extension = originalFileName.substring(i + 1);
			}
			Path path = Paths.get(LOCAL_DIRECTORY + updatedName + "." + extension);

			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (updatedName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + updatedName);
			}

			String originalFileName = file.getOriginalFilename();

			String extension = "";
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/downloadFile/")
					.path(generatedToken).toUriString();
			int i = originalFileName.lastIndexOf('.');
			if (i > 0) {
				extension = originalFileName.substring(i + 1);
			}
			DBFile dbFile = new DBFile(generatedToken, updatedName + "." + extension, file.getContentType(),
					file.getBytes(), fileDownloadUri);
			DBFile s = dbFileRepository.save(dbFile);
			DBFile dbFiles = dbFileRepository.findByToken(generatedToken);
			dbFiles.getDownloadUrl();
			
			//httpSession.setAttribute("url", dbFiles.getDownloadUrl());
			return s;
			
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + updatedName + ". Please try again!", ex);
		}
		
	}

	public DBFile getFile(String fileToken) {
		return (DBFile) dbFileRepository.findByToken(fileToken);

	}
	public void storeFile(MultipartFile file,Metting meeting) {

		String uuid = UUID.randomUUID().toString();
		String uid = uuid.toString().replaceFirst("[a-z]", uuid.toString().toUpperCase()).replace("-", "")
				.replaceFirst("[a-z]", uuid.toString().toLowerCase());
		String generatedToken = uid.substring(29, 37);

		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String dateTime = now.format(myFormatObj).replace(" ", "_").replace(":", "_");

		String updatedName = generatedToken + "_" + dateTime;
		try {

			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			
			String originalFileName = file.getOriginalFilename();

			String extension = "";

			int i = originalFileName.lastIndexOf('.');
			if (i > 0) {
				extension = originalFileName.substring(i + 1);
			}
			Path path = Paths.get(LOCAL_DIRECTORY + generatedToken);

			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			if (updatedName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + updatedName);
			}

			String originalFileName = file.getOriginalFilename();

			String extension = "";
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/downloadFile/")
					.path(generatedToken).toUriString();
			int i = originalFileName.lastIndexOf('.');
			if (i > 0) {
				extension = originalFileName.substring(i + 1);
			}
			DBFile dbFile = new DBFile(generatedToken,fileDownloadUri, updatedName + "." + extension, file.getContentType(),
					 file.getBytes(),meeting);
			 dbFileRepository.save(dbFile);
		} catch (IOException ex) {
			throw new FileStorageException("Could  not store file " + updatedName + ". Please try again!", ex);
		}
	}
}

