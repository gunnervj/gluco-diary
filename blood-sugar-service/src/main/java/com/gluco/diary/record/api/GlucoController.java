package com.gluco.diary.record.api;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gluco.diary.record.api.constants.CommonConstants;
import com.gluco.diary.record.api.model.RecordSugarRequest;
import com.gluco.diary.record.api.model.Recording;
import com.gluco.diary.record.api.service.IGlucoService;

@RestController
@RequestMapping(path=CommonConstants.BASE_PATH)
public class GlucoController implements GlucoAPI {
	private IGlucoService glucoService;
	
	@Autowired
	public GlucoController( IGlucoService glucoService ) {
		this.glucoService = glucoService;
	}

	@Override
	public ResponseEntity<?> recordSugarLevel(Principal user, RecordSugarRequest request) {
		if(glucoService.recordSugarLevel(user, request)) {
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<List<Recording>> getRecordings(Principal user, LocalDate date) {
		return new ResponseEntity<List<Recording>>(glucoService.getRecordings(user, date),HttpStatus.OK);
	}

}
