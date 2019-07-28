package com.gluco.diary.record.api.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recording {
	private LocalDate date;
	private List<SugarLevel> levels = new ArrayList<SugarLevel>();
	
	public Recording(LocalDate date, SugarLevel level) {
		this.date = date;
		this.levels.add(level);
	}
	
	public void addSugarLevel(SugarLevel level) {
		levels.add(level);
	}
	
	
	public boolean findAndRemove(SugarLevel level) {
		return levels.removeIf( lvl -> lvl.getFrequency().equalsIgnoreCase(level.getFrequency()) && lvl.getMeasurementType().equalsIgnoreCase(level.getMeasurementType()) );
	}
	
	
	
}
