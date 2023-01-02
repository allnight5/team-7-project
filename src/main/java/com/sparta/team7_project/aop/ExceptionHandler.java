package com.sparta.team7_project.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ExceptionHandler {

//    @AfterThrowing(value = "execution(public * com.sparta.team7_project.presentation.controller..*(..))", throwing= "exception")
//    public synchronized Object excute(ProceedingJoinPoint joinPoint, Exception exception) throws RuntimeException{
//        try{
//            Object output = joinPoint.proceed();
//            return output;
//        } catch (IllegalArgumentException e){
//            throw new IllegalArgumentException("");
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        }
//    }

}
