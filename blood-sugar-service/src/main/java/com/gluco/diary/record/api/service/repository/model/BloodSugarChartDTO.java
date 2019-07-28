package com.gluco.diary.record.api.service.repository.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gluco.diary.record.api.model.Recording;

import lombok.Data;

@Data
@Document(collection="sugar_chart")
public class BloodSugarChartDTO {
	@Id
	private String id;
	@Indexed(unique=true)
	private String email;
	private List<Recording> recordings = new ArrayList<Recording>();
	
	public void addRecording(Recording recording) {
		recordings.add(recording);
	}
	
	
	
	public Optional<Recording> findRecording(LocalDate date) {
		Optional<Recording> recordingOptional = recordings
													.stream()
													.filter(recordin -> recordin.getDate().equals(date))
													.findFirst();
		
		return recordingOptional;
	}
	
	public boolean removeRecording(LocalDate date) {
		return recordings.removeIf( rec -> rec.getDate().isEqual(date) );
	}
}
