package com.abhicodes.querydsldynamicquery.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class UsersDTO {
	
	private String fullName;
	private String userName;
	private String email;
}
