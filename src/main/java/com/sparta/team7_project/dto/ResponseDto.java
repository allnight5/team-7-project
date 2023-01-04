package com.sparta.team7_project.dto;

import com.sparta.team7_project.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDto<T> {
    private String message;

//    private boolean success;
    T data;
    private int statusCode;
//    Error error;
    public ResponseDto(String message, int statusCode){
        this.message = message;
        this.statusCode = statusCode;
    }
    public ResponseDto(T data){
        this.data = data;
    }

//    public ResponseDto(String message, int statusCode, T data){
//        this.message = message;
//        this.statusCode = statusCode;
//        this.data = data;
//    }

//    public ResponseDto(boolean success, T data, ErrorCode errorCode){
//        this.success = success;
//        this.data = data;
//        this.error = new Error(errorCode);
//    }
}
