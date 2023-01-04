package com.sparta.team7_project.exception;


import com.sparta.team7_project.exception.dto.ApiExceptionResponseDto;
import com.sun.jdi.request.DuplicateRequestException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
// extends ResponseEntityExceptionHandler ?????mvc의 모든 예외를 오버로딩 해서
//처리해주는것일탠데 있으면 MethodArgumentNotValidException가 있으니 오류가난다...
//mvc모델이 아니긴해도 오류날것은 아닌것 같았는데.. 중복처리 오류가난다.
public class ApiExceptionHandler{
//    @ExceptionHandler(value = { IllegalArgumentException.class })
//    @ExceptionHandler({IllegalArgumentException.class, TokenMgrError.class})
//        log.warn(exception.getMessage());
//        return new ResponseEntity<>(
//    restApiException,
//    HttpStatus.BAD_REQUEST
//        );
//    찾는 내용이 없을때 하는 예외처리
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiExceptionResponseDto handleApiRequestException(IllegalArgumentException exception) {
        log.warn(exception.getMessage());
        return new ApiExceptionResponseDto(exception.getMessage(),400);
    }
    //중복자가 있을때 발생하는 예외 처리
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateRequestException.class)
    public ApiExceptionResponseDto handleDuplicateRequestException(DuplicateRequestException exception){
        log.warn(exception.getMessage());
        return new  ApiExceptionResponseDto(exception.getMessage(),400);
    }
    //    사용자의 권한이 없을때 하는 예외처리
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SecurityException.class)
    public ApiExceptionResponseDto handleApiSecurityRequestException(SecurityException exception) {
        log.warn(exception.getMessage());
        return new ApiExceptionResponseDto(exception.getMessage(),400);
    }

    //    jwt토큰 규칙이 틀렸을때 하는 예외처리
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MalformedJwtException.class)
    public ApiExceptionResponseDto handleSecurityRequestException(SecurityException exception) {
        log.warn(exception.getMessage());
        return new  ApiExceptionResponseDto(exception.getMessage(), 400);
    }

    //회원가입시 vaild 규칙이 틀렸을때 이루어지는 예외처리
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiExceptionResponseDto handleValidationException(MethodArgumentNotValidException exception){
        log.warn(exception.getMessage());
        return new ApiExceptionResponseDto(exception.getFieldError().getDefaultMessage(), 400);
    }


    //Controller단 이외에서 발생하는 예외처리는 advice로 처리되지 않는다.
    //다른말로 Controller단으로 오기전에or Controller단에서 시작하기전에 보내오는 메소드내에서 오류가나서
    //예외를 발생시킬시 Advice는 잡아낼수없다.
    //사용자가 존재하지 않을때 하는 예외 처리
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ApiExceptionResponseDto handleUsernameNotFoundException(UsernameNotFoundException exception){
        log.warn(exception.getMessage());
        return new  ApiExceptionResponseDto(exception.getMessage(),400);
    }

    // protected 쓴 이유는 상속을받는 녀석 말고는 사용하지 않기 위해서
    //제 3의 방식 커스텀을 해서 예외발생을 커스텀으로 만들어줘서 처리한다.
    //커스텀할필요없이.. new IllegalArgumentException 으로 다 통일하는거랑 커스텀해서하는거랑 차이는없는데
    //나는 변경해서 예외처리를 해봤다 같은 느낌이다.
//    @ExceptionHandler(value = {CustomerException.class})  // 익셉션할 클래스 지정
//    protected ResponseEntity<ErrorResponse> handleCustomException(CustomerException ex) {
//        return ErrorResponse.toResponseEntity(ex.getErrorCode());
//    }
}