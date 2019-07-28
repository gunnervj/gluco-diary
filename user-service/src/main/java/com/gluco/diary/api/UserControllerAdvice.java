package com.gluco.diary.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gluco.diary.api.constants.ERROR_CODES;
import com.gluco.diary.api.domain.Response;
import com.gluco.diary.api.exceptions.AlreadyExistException;
import com.gluco.diary.api.exceptions.InvalidTokenException;
import com.gluco.diary.api.exceptions.UnknownException;
import com.gluco.diary.api.exceptions.ValidationException;
import com.gluco.diary.api.domain.Error;

@ControllerAdvice
public class UserControllerAdvice extends ResponseEntityExceptionHandler {

	   @ExceptionHandler(value = UnknownException.class)
	   public ResponseEntity<Object> handleUnknownException(UnknownException exception) {
		 Response response = new Response();
		 List<Error> errorList = new ArrayList<>();
		 response.setErrors(errorList);
		 Error error = createError(exception.getErrorCode());
		 errorList.add(error);

		 return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
	   }
		
	   @ExceptionHandler(value = AlreadyExistException.class)
	   public ResponseEntity<Object> handleAlreadyExistException(AlreadyExistException exception) {
		 Response response = new Response();
		 List<Error> errorList = new ArrayList<>();
		 response.setErrors(errorList);
		 Error error = createError(exception.getErrorCode());
		 errorList.add(error);
		 return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	   }
	   
	   @ExceptionHandler(value = ValidationException.class)
	   public ResponseEntity<Object> handleValidationException(ValidationException exception) {
		 Response response = new Response();
		 List<Error> errorList = new ArrayList<>();
		 response.setErrors(errorList);
				 
		 Error error = createError(exception.getErrorCode());
		 errorList.add(error);
	     return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	   }
	   
	   
	   @ExceptionHandler(value = InvalidTokenException.class)
	   public ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException exception) {
		 Response response = new Response();
		 List<Error> errorList = new ArrayList<>();
		 response.setErrors(errorList);
				 
		 Error error = createError(exception.getErrorCode());
		 errorList.add(error);
	     return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	   }
	   
	   @Override
	   protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	       final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
	       Response response = new Response();
	  	   List<Error> errorList = fieldErrors.stream().map( errorDet -> {
	    	   Error error = new Error();
	    	   error.setMessage("Invalid " + errorDet.getField() + ". " + errorDet.getDefaultMessage());
	    	   error.setCode(ERROR_CODES.INVALID_REQUEST.getCode());
	    	   return error;
	       }).collect(Collectors.toList());
	  	   response.setErrors(errorList);
	  	   return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);  
	   }
	   
	   	@Override
		protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
				HttpStatus status, WebRequest request) {
		   Response response = new Response();
		   List<Error> errorList = new ArrayList<>();
	 	   Error error = new Error();
	 	   error.setMessage("Invalid " + ex.getPropertyName() + ". " + ex.getMessage());
	 	   error.setCode(ERROR_CODES.INVALID_REQUEST.getCode());
		   response.setErrors(errorList);
		   return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);  
		}
	   
	   @Override
	    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
				WebRequest request) {
	       final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
	       Response response = new Response();
	  	   List<Error> errorList = fieldErrors.stream().map( errorDet -> {
	    	   Error error = new Error();
	    	   error.setMessage("Invalid " + errorDet.getField() + ". " + errorDet.getDefaultMessage());
	    	   error.setCode(ERROR_CODES.INVALID_REQUEST.getCode());
	    	   return error;
	       }).collect(Collectors.toList());
	  	   response.setErrors(errorList);
	  	   return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);  
		}

		private Error createError(ERROR_CODES errorType) {
			   Error error = new Error();
			   error.setCode(errorType.getCode());
			   error.setMessage(errorType.getMessage());
			   return error;
		}
	   
	}
