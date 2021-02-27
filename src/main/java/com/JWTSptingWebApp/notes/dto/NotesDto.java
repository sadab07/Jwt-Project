package com.JWTSptingWebApp.notes.dto;

import com.JWTSptingWebApp.notes.model.NotesDetail;

public class NotesDto {

	private String note;
	

	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public String getNotetoken() {
		return notetoken;
	}


	public void setNotetoken(String notetoken) {
		this.notetoken = notetoken;
	}


	private String notetoken;


	public NotesDto() {
	}
	
	public NotesDto(NotesDetail detail)
	{
		this.note= detail.getNote();
		this.notetoken=detail.getNotetoken();
	}


	public NotesDto(String note, String notetoken) {
		this.note = note;
		this.notetoken = notetoken;
	}
	
}



