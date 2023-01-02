package com.sparta.team7_project.exception.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ApiExceptionDto {
    private String errorMessage;
    private HttpStatus httpStatus;
    public ApiExceptionDto(String errorMessage, HttpStatus httpStatus){
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}