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

// 3가지 단계를 나누어서 생각
//1단계 Dto만 만들고 try catch   // 모든 매서드 모든 에러경우마다? 힘드러


//2단계 컨트롤러에 예외처리를 추가 by @ExceptionHandler!  //매 컨트롤러마다? 힘드러


//3단계 글로벌 예외처리.. ! RestControllerAdvice