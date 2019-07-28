package com.gluco.diary.record.api;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.gluco.diary.record.api.constants.CommonConstants;
import com.gluco.diary.record.api.model.RecordSugarRequest;
import com.gluco.diary.record.api.model.Recording;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(description = "Service for recording and retrieving blood glucose level.", basePath=CommonConstants.BASE_PATH, tags= "User")
public interface GlucoAPI {
	@PostMapping
	@ApiOperation(value="Service to add a new recording.")
	@ApiResponses( value= {
			@ApiResponse(code=201, message="Success"),
			@ApiResponse(code=400, message="Invalid Input", response=Error.class),
			@ApiResponse(code=503, message="Under Maintenance", response=Error.class)
	})
	public ResponseEntity<?> recordSugarLevel(@ApiIgnore Principal user, @RequestBody @Valid RecordSugarRequest request);

	
	@GetMapping
	@ApiOperation(value="Service to retrieve recordings. You can pass a date to filter result.")
	@ApiResponses( value= {
			@ApiResponse(code=201, message="Success"),
			@ApiResponse(code=400, message="Invalid Input", response=Error.class),
			@ApiResponse(code=503, message="Under Maintenance", response=Error.class)
	})
	public ResponseEntity<List<Recording>> getRecordings(@ApiIgnore Principal user, 
											@ApiParam(name = "date", value = "Date for the reading in yyyy-MM-dd.", required = false, example = "2020-05-15") 
											@RequestParam(value = "date", required = false)
											@DateTimeFormat(pattern="yyyy-MM-dd")
											LocalDate date);
	
}
