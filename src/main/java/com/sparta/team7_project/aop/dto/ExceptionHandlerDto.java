package com.sparta.team7_project.aop.dto;

import org.springframework.http.HttpStatus;

public class ExceptionHandlerDto {
    private String message;
    private HttpStatus httpStatus;

    public ExceptionHandlerDto(String message, HttpStatus httpStatus){
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
