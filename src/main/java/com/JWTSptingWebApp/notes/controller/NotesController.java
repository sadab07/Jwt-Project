package com.JWTSptingWebApp.notes.controller;

import java.util.HashMap;
import java.util.Map;


import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.JWTSptingWebApp.MettingMng.Model.Metting;
import com.JWTSptingWebApp.notes.dto.NotesDto;
import com.JWTSptingWebApp.notes.model.NotesDetail;
import com.JWTSptingWebApp.notes.payload.NotePayload;
import com.JWTSptingWebApp.notes.repository.NotesRepo;
import com.JWTSptingWebApp.notes.service.NotesService;
import com.JWTSptingWebApp.notes.serviceimpl.NotesServiceimpl;


@RestController
@RequestMapping("/note")
@Transactional
public class NotesController {
	HttpSession httpSession;
	
	@Autowired
	NotesService notesService;
	
	@Autowired
	NotesServiceimpl notesServiceimpl;
	
	@Autowired
	NotesRepo notesRepo;
	
	@Autowired
	NotePayload notepayload;


	NotesDto notesDto;
	
	@GetMapping("/getall")
	public ResponseEntity<?> getAllNotess() {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("message", "Got all notes successfully");
		map.put("code", 200);
		map.put("status", true);
		map.put("data", notesService.getAllNotes());
		return ResponseEntity.ok(map);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String, Object> createOrUpdateNotes(@RequestBody NotesDto notesDto,Metting metting,HttpSession httpSession) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		notesService.createOrUpdateNotes(notesDto, metting,httpSession);
		map.put("code", 200);
		map.put("message", "Note created successfully");
		map.put("status", true);
		map.put("data",new NotePayload((String) httpSession.getAttribute("notetoken"), notesDto.getNote()));
		return map;
	}	
	@PutMapping("/update")
	public ResponseEntity<?> editnotes(@RequestBody NotesDto notesDto) {
		Map<String, Object> map = new HashMap<String, Object>();

		String notetoken = notesDto.getNotetoken();
		String note = notesDto.getNote();
		NotesDetail notesDetail = notesRepo.findByNotetoken(notetoken);
		
		if(notesDetail != null)
		{
			notesService.editNote(notesDto);
			map.put("message",  "Note updated successfully");
			map.put("code", 200);
			map.put("status", true);
			map.put("notetoken", notetoken);
			map.put("note", note); 
		}
		else
		{
			map.put("message",  "Note not found");
			map.put("code", 404);
			map.put("status", false);
			map.put("notetoken", notetoken);
			map.put("note", note);
		}
		return ResponseEntity.ok(map);
	}
	
	@GetMapping(value = "/findNoteByToken/{notetoken}")
	public ResponseEntity<?> findNoteByToken(@PathVariable("notetoken") String notetoken) {
		Map<String, Object> map = new HashMap<String, Object>();
		NotesDetail notes = notesRepo.findByNotetoken(notetoken);
		if (null == notes) {
			map.put("code", 404);
			map.put("message", "Token does not found");
			map.put("status", false);
			map.put("token", notetoken);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		}
		else {
			map.put("message", "Found By token note successfully");
			map.put("code", 200);
			map.put("status", true);
			map.put("notetoken",notes.getNote());
			map.put("notes",notes.getNotetoken());
			
		//	map.put("data", notesService.findByNoteToken(notetoken));
		}
			return ResponseEntity.ok(map);
		
	}
	@DeleteMapping("/delete/{notetoken}")
	public ResponseEntity<?> delete(@PathVariable String notetoken, NotesDto notesDto) {

		Map<String, Object> map = new HashMap<String, Object>();
		NotesDetail notesDetail = (NotesDetail) notesRepo.findByNotetoken(notetoken);
		if (null == notesDetail) {
			map.put("code", 404);
			map.put("message", "Note does not found");
			map.put("status", false);
			map.put("token", notetoken);
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(map);
		} else {
			notesRepo.deleteByNotetoken(notetoken);
			map.put("code", 200);
			map.put("Data", notetoken);
			map.put("message", "Delete Successfully");
			map.put("status", true);
			return ResponseEntity.ok(map);
		}
	}
}

