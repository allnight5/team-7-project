package com.sparta.team7_project.exception;


import com.sparta.team7_project.exception.dto.ApiExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@Slf4j
//@RestControllerAdvice
public class ApiExceptionHandler {
//    @ExceptionHandler(value = { IllegalArgumentException.class })
//    @ExceptionHandler({IllegalArgumentException.class, TokenMgrError.class})
    //2개이상의 예외를 처리하고 싶을때
//    @ExceptionHandler(IllegalArgumentException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<Object> handleApiRequestException(IllegalArgumentException ex) {
//        ApiExceptionDto restApiException = new ApiExceptionDto(ex.getMessage(), HttpStatus.BAD_REQUEST);
//        log.warn(ex.getMessage());
//        return new ResponseEntity<>(
//                restApiException,
//                HttpStatus.BAD_REQUEST
//        );
//    }
}