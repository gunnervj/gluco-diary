package com.gluco.diary.record.api.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import com.gluco.diary.record.api.model.RecordSugarRequest;
import com.gluco.diary.record.api.model.Recording;

public interface IGlucoService {
	public boolean recordSugarLevel(Principal user, RecordSugarRequest request);
	public List<Recording> getRecordings(Principal user, LocalDate date);
}
