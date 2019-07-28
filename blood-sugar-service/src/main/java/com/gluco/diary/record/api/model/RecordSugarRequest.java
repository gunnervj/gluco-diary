package com.gluco.diary.record.api.model;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Valid
public class RecordSugarRequest {
	@ApiModelProperty(name = "date", value = "Date for the recording", required = true)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate date;
	@ApiModelProperty(name="sugarLevel", value = "Sugar Level Details")
	@Valid
	private SugarLevel sugarLevel;
}
