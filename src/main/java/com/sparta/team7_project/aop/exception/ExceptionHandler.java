package com.sparta.team7_project.aop.exception;

import com.sparta.team7_project.presentation.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ExceptionHandler {

//    @Around(value = "execution(public * com.sparta.team7_project.presentation.controller.PostController.updatePost(..))")
//    public Object updatePostException(ProceedingJoinPoint joinPoint)throws Throwable {
//        try{
//            Object output = joinPoint.proceed();
//            return output;
//        }catch (IllegalArgumentException e) {
//            return new MessageResponseDto(e.getMessage(), 400);
//        }
//    }
    @Around(value = "execution(public * com.sparta.team7_project.presentation.controller.PostController..*(..))")
    public Object PostException(ProceedingJoinPoint joinPoint)throws Throwable {
        try{
            Object output = joinPoint.proceed();
            return output;
        }catch (IllegalArgumentException e) {
            return new MessageResponseDto(e.getMessage(), 400);
        }
    }

    @Around(value = "execution(public * com.sparta.team7_project.presentation.controller.CommentController..*(..))")
    public Object commentException(ProceedingJoinPoint joinPoint)throws Throwable {
        try{
            Object output = joinPoint.proceed();
            return output;
        }catch (IllegalArgumentException e) {
            return new MessageResponseDto(e.getMessage(), 400);
        }
    }
    @Around(value = "execution(public * com.sparta.team7_project.security.service.UserDetailsServiceImpl.loadUserByUsername(..))")
    public Object securityException(ProceedingJoinPoint joinPoint)throws Throwable {
        try{
            Object output = joinPoint.proceed();
            return output;
        }catch (RuntimeException e) {
//            return new MessageResponseDto(e.getMessage(), 400);
            log.info("사용자를 찾을 수 없습니다");
        }
        return false;
    }
//    @Pointcut(value = "execution(public * com.sparta.team7_project.presentation.controller.PostController.*.*(..))")
//    public void TestPoine(){}
//    @AfterThrowing(pointcut = "TestPoine()", throwing= "exception")
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
