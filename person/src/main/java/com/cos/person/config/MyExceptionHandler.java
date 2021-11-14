package com.cos.person.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.person.domain.ResponseDto;

@RestController
@ControllerAdvice
public class MyExceptionHandler{
	
	@ExceptionHandler(value=Exception.class)
	public ResponseDto<String> wrongReq(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
}
