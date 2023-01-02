package com.sparta.team7_project.exception.dto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiExceptionDto {
    private String errorMessage;
    private HttpStatus httpStatus;

//    RestApiExceptionDto(String errorMessage, HttpStatus httpStatus){
//        this.errorMessage = errorMessage;
//        this.httpStatus = httpStatus;
//    }
}