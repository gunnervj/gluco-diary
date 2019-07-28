package com.gluco.diary.record.api.service.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.gluco.diary.record.api.service.repository.model.BloodSugarChartDTO;

@Repository
public interface BloodSugarChartRepository extends MongoRepository<BloodSugarChartDTO, String> {
	public BloodSugarChartDTO findByEmail(String email, Sort sort);
}
