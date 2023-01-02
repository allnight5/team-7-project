package com.sparta.team7_project.aop;

import com.sparta.team7_project.aop.dto.ExceptionHandlerDto;
import com.sparta.team7_project.aop.dto.ExceptionResponseDto;
import com.sparta.team7_project.presentation.dto.MessageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;


@Slf4j
@Aspect
@Component
public class ExceptionHandler {

    @Around(value = "execution(public * com.sparta.team7_project.presentation.controller.PostController.updatePost(..))")
    public synchronized Object updatePostException(ProceedingJoinPoint joinPoint)  throws Throwable {
        try{
            joinPoint.proceed();
        }catch (IllegalArgumentException exception){
            return new MessageResponseDto(exception.getMessage(), 400);
        }
        return joinPoint;
    }

    @Around(value = "execution(public * com.sparta.team7_project.security.service.UserDetailsServiceImpl.loadUserByUsername(..))")
    public synchronized Object swallowException(ProceedingJoinPoint joinPoint)  throws Throwable {
        try{
            joinPoint.proceed();
            return joinPoint;
        }catch (UsernameNotFoundException exception){
            MessageResponseDto msg = new MessageResponseDto(exception.getMessage(), 400);
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }

//    @Pointcut(value = "execution(public * com.sparta.team7_project.presentation.controller.PostController.updatePost(..))")
//    public void printOperation(){}
//    @AfterThrowing(value = "printOperation()", throwing= "e")
//    public MessageResponseDto allException(IllegalArgumentException e)throws Throwable{
//            return new MessageResponseDto(e.getMessage(), 400);
//    }

}
