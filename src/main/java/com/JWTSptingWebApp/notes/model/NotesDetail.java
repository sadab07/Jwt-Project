package com.JWTSptingWebApp.notes.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.JWTSptingWebApp.MettingMng.Dto.MettingDto;
import com.JWTSptingWebApp.MettingMng.Model.Metting;

@Entity
@Table
public class NotesDetail {
	@Id
//	@Column(name="note_token")
	private String notetoken;
	
	@Override
	public String toString() {
		return "NotesDetail [notetoken=" + notetoken + ", note=" + note + "]";
	}
	@Column(name="note")
	private String note;
	@ManyToOne(cascade = CascadeType.REMOVE)
	Metting metting;
	
	public Metting getMetting() {
		return metting;
	}
	public void setMetting(Metting metting) {
		this.metting = metting;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getNotetoken() {
		return notetoken;
	}
	public NotesDetail(String notetoken, String note,Metting metting) {
		this.notetoken = notetoken;
		this.note = note;
		this.metting=metting;
	}
	
	public void setNotetoken(String notetoken) {
		this.notetoken = notetoken;
	}
	public NotesDetail() {}
	public NotesDetail(String notetoken2, String note2) {
		this.notetoken = notetoken2;
		this.note = note2;
	}
}