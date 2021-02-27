package com.JWTSptingWebApp.notes.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.JWTSptingWebApp.MettingMng.Model.Metting;
import com.JWTSptingWebApp.notes.dto.NotesDto;
import com.JWTSptingWebApp.notes.model.NotesDetail;
@Service
public interface NotesService {
	
public NotesDetail createNotes(String notes,Metting metting);
	
	public List<NotesDto> getAllNotes();
	
	public void deleteByToken(String notetoken);
	
	public NotesDetail findByNoteToken(String notetoken);
	
	 public ResponseEntity<?> editNote(NotesDto notesDto);
//	 public NotesDetail editNote(NotesDto notesDto);

	public NotesDetail createOrUpdateNotes(NotesDto notesDto,Metting metting,HttpSession httpSession);
	
}
