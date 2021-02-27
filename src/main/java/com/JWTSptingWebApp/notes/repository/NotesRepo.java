package com.JWTSptingWebApp.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JWTSptingWebApp.notes.dto.NotesDto;
import com.JWTSptingWebApp.notes.model.NotesDetail;

@Repository
public interface NotesRepo extends JpaRepository<NotesDetail, String>{

	void deleteByNotetoken(String notetoken);

    NotesDetail findByNotetoken(String notetoken);
	//NotesDetail (String notetoken);

	boolean existsByNotetoken(String notetoken);

	NotesDetail findByNote(String note);

	 //String[] findByMetting_token(String token);
		

//	boolean existsByToken(String notetoken);


	}
