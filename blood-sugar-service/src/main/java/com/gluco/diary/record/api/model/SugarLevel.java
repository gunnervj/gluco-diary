package com.gluco.diary.record.api.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SugarLevel {
	@ApiModelProperty(value = "Sugar Level in mg/dl")
	@Min(value = 60, message = "Cannot be less than 60mg/dl")
	@Max(value = 400, message = "Cannot be more than 400mg/dl")
	private Integer level;
	@ApiModelProperty(value = "When is this recorded for? Morning, Afternoon or Night?", allowableValues = "Morning,Afternoon,Night")
	private String frequency;
	@ApiModelProperty(value = "Is this After or Before Food?", allowableValues = "PRE_MEAL,POST_MEAL")
	private String measurementType;
		
}
