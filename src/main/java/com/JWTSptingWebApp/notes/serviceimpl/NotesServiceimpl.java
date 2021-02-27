package com.JWTSptingWebApp.notes.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.JWTSptingWebApp.MettingMng.Dto.MettingDto;
import com.JWTSptingWebApp.MettingMng.Model.Metting;
import com.JWTSptingWebApp.notes.dto.NotesDto;
import com.JWTSptingWebApp.notes.model.NotesDetail;
import com.JWTSptingWebApp.notes.repository.NotesRepo;
import com.JWTSptingWebApp.notes.service.NotesService;

@Service
public class NotesServiceimpl implements NotesService {
	
	@Autowired
	NotesRepo notesRepo;
	@Autowired
	private MettingDto mettingDto;

	public NotesDetail createNotes(String note, Metting metting) {
		UUID uuid = UUID.randomUUID();
		String uid = uuid.toString().replaceFirst("[a-z]", uuid.toString().toUpperCase()).replace("-", "")
				.replaceFirst("[a-z]", uuid.toString().toLowerCase());
		String notetoken = uid.substring(29, 37);
		NotesDetail notes = new NotesDetail(notetoken,note,metting);

		return notesRepo.save(notes);
	}

	@Override
	public List<NotesDto> getAllNotes() {
		List<NotesDetail> list = notesRepo.findAll();
		List<NotesDto> noteslist = list.stream().map(NotesDto::new).collect(Collectors.toCollection(ArrayList::new));
		return noteslist;

	}

	@Override
	public void deleteByToken(String notetoken) {
		notesRepo.deleteByNotetoken(notetoken);

	}

	@Override
	public ResponseEntity<?> editNote(NotesDto notesDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (notesRepo.existsByNotetoken(notesDto.getNotetoken())) {
			notesDto.setNotetoken(notesDto.getNotetoken());
			
			NotesDetail notes = new NotesDetail(notesDto.getNotetoken(), notesDto.getNote(),notesRepo.findByNotetoken(notesDto.getNotetoken()).getMetting());
			Metting metting = notes.getMetting();
			
//			String meetingToken = notesRepo.findByNotetoken(notesDto.getNotetoken()).getMetting();
//			System.err.println(meetingToken);
//			mettingDto.setToken(meetingToken);
			
//			meeting.setToken(notesRepo.findByNotetoken(notesDto.getNotetoken()).getMetting().getToken());
			
			notesRepo.save(notes);
		}
		return ResponseEntity.ok(map);
	}

	private NotesDetail convertDtoToModel(NotesDto dto) {

		NotesDetail detail = new NotesDetail();
		detail.setNote(detail.getNote());
		return detail;
	}

	private NotesDto convertModelToDTO(NotesDetail detail) {

		NotesDto dto = new NotesDto();

		dto.setNote(detail.getNote());
		return dto;
	}

	@Override
	public NotesDetail findByNoteToken(String notetoken) {
		return notesRepo.findByNotetoken(notetoken);

	}

	@Override
	public NotesDetail createOrUpdateNotes(NotesDto notesDto, Metting metting,HttpSession httpSession) {
		NotesDetail detail = convertDtoToModel(notesDto);
		UUID uuid = UUID.randomUUID();
		String uid = uuid.toString().replaceFirst("[a-z]", uuid.toString().toUpperCase()).replace("-", "")
				.replaceFirst("[a-z]", uuid.toString().toLowerCase());
		String notetoken = uid.substring(29, 37);
		httpSession.setAttribute("notetoken", notetoken);
		NotesDetail notes = new NotesDetail(notetoken,notesDto.getNote());
		notesRepo.save(notes);	
		return detail;
	}
}