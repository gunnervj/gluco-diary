package com.gluco.diary.record.api.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gluco.diary.record.api.constants.ERROR_CODES;
import com.gluco.diary.record.api.exceptions.NotFoundException;
import com.gluco.diary.record.api.exceptions.UnknownException;
import com.gluco.diary.record.api.exceptions.ValidationException;
import com.gluco.diary.record.api.model.Frequency;
import com.gluco.diary.record.api.model.MeasurementType;
import com.gluco.diary.record.api.model.RecordSugarRequest;
import com.gluco.diary.record.api.model.Recording;
import com.gluco.diary.record.api.service.repository.BloodSugarChartRepository;
import com.gluco.diary.record.api.service.repository.model.BloodSugarChartDTO;

@Service
public class GlucoService implements IGlucoService {
	private BloodSugarChartRepository bloodSugarChartRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(GlucoService.class.getName());
	
	@Autowired
	public GlucoService(BloodSugarChartRepository bloodSugarChartRepository) {
		this.bloodSugarChartRepository = bloodSugarChartRepository;
	}
	
	@Override
	public boolean recordSugarLevel(Principal user, RecordSugarRequest request) {
		boolean isNew = true;
		try {
			validateRequest(request);
			Sort sort = new Sort(Sort.Direction.DESC, "date");
			BloodSugarChartDTO chart = bloodSugarChartRepository.findByEmail(user.getName(), sort);
			if( null == chart ) {
				addNewBloodSugarChart(user, request);
			} else {
				createOrUpdateRecording(chart, request);
				isNew = false;
			}
		} catch (ValidationException ex) {
			throw ex;
		} catch (Exception ex) {
			LOGGER.error("Error while recording blood sugar level.Error:" + ex.getMessage(), ex);
			throw new UnknownException(ERROR_CODES.UNDER_MAINTENANCE);
		}
		
		return isNew;
	}
	
	private void createOrUpdateRecording(BloodSugarChartDTO chart, RecordSugarRequest request) {
		Optional<Recording> recordingOptional = chart.findRecording(request.getDate());
		Recording recording = null;
		if( recordingOptional.isPresent() ) {
			chart.removeRecording(request.getDate());
			recording = recordingOptional.get();
			recording.findAndRemove(request.getSugarLevel());
			recording.addSugarLevel(request.getSugarLevel());
		} else {
			recording = new Recording(request.getDate(), request.getSugarLevel());
		}
		
		addNewRecording(recording, chart, false);
	}
	
	private void validateRequest(RecordSugarRequest request) throws ValidationException {
		if(request.getSugarLevel().getFrequency() == null ||  !Frequency.isValid(request.getSugarLevel().getFrequency()) ) {
			throw new ValidationException(ERROR_CODES.FREQUENCY_REQUIRED);
		}
		
		if( request.getDate() == null || request.getDate().isAfter(LocalDate.now()) ) {
			throw new ValidationException(ERROR_CODES.INVALID_DATE);
		}
		
		if(request.getSugarLevel().getMeasurementType() == null || !MeasurementType.isValid(request.getSugarLevel().getMeasurementType()) ) {
			throw new ValidationException(ERROR_CODES.MEASUREMENT_REQUIRED);
		}
	}

	private void addNewBloodSugarChart(Principal user, RecordSugarRequest request) {
			BloodSugarChartDTO chart = new BloodSugarChartDTO();
			chart.setEmail(user.getName());
			addNewRecording(new Recording(request.getDate(), request.getSugarLevel()), chart, true);
	}

	private void addNewRecording(Recording recording, BloodSugarChartDTO chart,boolean isNew) {
		chart.addRecording(recording);
		if(isNew) {
			bloodSugarChartRepository.insert(chart);
		} else {
			bloodSugarChartRepository.save(chart);
		}
	}
	

	@Override
	public List<Recording> getRecordings(Principal user, LocalDate date) {
		List<Recording> recordingsSorted = new ArrayList<>();
		List<Recording> recordings = new ArrayList<>();
		try {
			BloodSugarChartDTO chart = null;
			Sort sort = new Sort(Sort.Direction.DESC, "recordings[].date");
			chart = bloodSugarChartRepository.findByEmail(user.getName(), sort);
			if( null == chart ) {
				throw new NotFoundException(ERROR_CODES.READING_NOT_FOUND);
			} else {
				if( null != date) {
					LOGGER.info("Finding Readings for date = " + date.toString());
					chart.getRecordings().stream()
							.filter(rec-> rec.getDate().equals(date))
							.findFirst()
							.map(recording -> recordings.add(recording))
							.orElseThrow( () -> new NotFoundException(ERROR_CODES.READING_NOT_FOUND));
				} else {
					chart.getRecordings().forEach(recording -> {
						recordings.add(recording);
					});
					
				}
			}
			recordingsSorted = recordings
									.stream()
									.sorted(Comparator.comparing(Recording::getDate).reversed()).collect(Collectors.toList());
		} catch (NotFoundException ex) {
			throw ex;
		} catch (Exception ex) {
			LOGGER.error("Error while getting blood sugar level.Error:" + ex.getMessage(), ex);
			throw new UnknownException(ERROR_CODES.UNDER_MAINTENANCE);
		}
		
		return recordingsSorted;
	}

}
