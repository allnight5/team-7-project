package com.sparta.team7_project.exception.dto;

import com.sparta.team7_project.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerException extends RuntimeException {
    // RuntimeException 을 상속하는 이유는 특정짓지 않았고
    // RuntimeException 은 개발자가 의도적으로 잡아주는 예외처리로 많이 쓰인다.
    private final ErrorCode errorCode;
}