package com.gluco.diary.api.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(value=Include.NON_EMPTY)
public class User {
	@NotEmpty(message="Name cannot be Empty")
	@ApiModelProperty(name="Name", example="John Connor", allowEmptyValue=false, dataType="String",required=true)
	private String name;
	@NotEmpty()
	@Pattern(regexp="^(Male|Female)$",message="Can be either Male or Female")
	@ApiModelProperty(name="gender", example="Male", allowEmptyValue=false, dataType="String",required=true, allowableValues="Male,Female")
	private String gender;
	@NotNull(message="Email cannot be empty.")
	@Email
	@ApiModelProperty(name="Email", example="John@example.com", allowEmptyValue=false, dataType="String",required=true)
	private String email;
	@NotEmpty(message="Password cannot be empty")
	@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message= "Password should contain atleast one upper case and one lower case character with numbers and a special symbol(@ or # or $ or %). It should be a minium of 8 characters long.")
	@ApiModelProperty(name="Password", example="Welcome123$", allowEmptyValue=false, dataType="String",required=true, notes="Should contain atleast one Upper Case character with numbers and a special symbol(@ or # or $ or %).")
	private String password = "";
}
