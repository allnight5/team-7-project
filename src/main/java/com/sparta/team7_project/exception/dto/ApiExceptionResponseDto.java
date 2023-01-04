package com.sparta.team7_project.exception.dto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiExceptionResponseDto {
    private String errorMessage;
    private int Status;

    public ApiExceptionResponseDto(String errorMessage, int Status){
        this.errorMessage = errorMessage;
        this.Status = Status;
    }
}