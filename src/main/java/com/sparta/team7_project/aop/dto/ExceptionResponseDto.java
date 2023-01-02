package com.sparta.team7_project.aop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExceptionResponseDto<T> {
    T data;

    public ExceptionResponseDto(T data){
        this.data =data;
    }
}
