package com.JWTSptingWebApp.notes.payload;

import org.springframework.stereotype.Service;

@Service
public class NotePayload {

	private String notetoken;
	public String getNotetoken() {
		return notetoken;
	}


	public void setNotetoken(String notetoken) {
		this.notetoken = notetoken;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	private String note;
	
	
	public NotePayload() {
	}


	public NotePayload(String notetoken, String note) {
		super();
		this.notetoken = notetoken;
		this.note = note;
	}
}
