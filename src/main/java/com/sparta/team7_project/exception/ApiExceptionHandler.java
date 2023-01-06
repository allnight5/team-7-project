package com.sparta.team7_project.exception;


import com.sparta.team7_project.exception.dto.ApiExceptionResponseDto;
import com.sun.jdi.request.DuplicateRequestException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
/*
(Simple Logging Facade For Java),로깅 추상화 라이브러리!  (즉 추상화기때문에 구현프레임워크를 logback을 채택중...? 사용)?
라이브러리? 난 설치안했는데? 스프링부트 만들때 로깅 관련 의존성 알아서 깔아줌 !

 Logging 이뭐냐면 https://www.youtube.com/watch?v=1MD5xbwznlI&t=650s  //우테코톡 1편 2편 이있다   Facade 는? 디자인패턴중 하나라고 알아만 두면..좋다 외관~ 인터페이스를 만드는느김

sout ->TEST코드 -> 로깅... 배표환경에서 확인....

 로깅은 프로그램동작시 발생하는 모든 일을 기록하는 행위

 모든일 ~ 서비스의 동작(에러) +장애(exception.error) //기록~ sout or  ,,로깅프레임워크이용(SLF4J ,,Logback,,Lof4j등이 있다)

로깅기능을 이용하면 출력형식을 지정 로그 레벨에 따라 남기고싶은 로그를 별도로 지정가능.. 콘솔뿐만아닌 파일이나 네트워크등.. 로그를 별도 위치에 남길 수있다

로그 레벨? ->
                   Fatal /   Error 외부의 문제(API요청에 에러..!)/종료의 +외부 구분//
          잠재적가능/ Warn/의도한 에러or상태변경과같은

         정보성메세지 Info/
          개발환경 //Debug/Trace

왜 디버깅기능을 안쓰냐... 실서버 구동중에는 디버깅을 이용하기 힘드니까... 이럴때 로깅을 선택하는것 디버깅할수 있는 상황이라면 디버깅하숍!..


진짜 간단한 요약 왜씀? log.~~쓰기 위해 달아놈 안달아 놓으면 Logger 객체를 만들어야함아니라면 Logger ~ 해서 객체 생성해야함*/



@RestControllerAdvice
  /*      (효근)
// extends ResponseEntityExceptionHandler ?????mvc의 모든 예외를 오버로딩 해서
//처리해주는것일탠데 있으면 MethodArgumentNotValidException가 있으니 오류가난다...
//mvc모델이 아니긴해도 오류날것은 아닌것 같았는데.. 중복처리 오류가난다.*/
     /*   (재혁)
이것도 Controller랑 비슷하다 @RestController 있는것처럼 이것도 @RetControllerAdvice가 있는것...
그러면 ControllerAdvice가 먼지 알아보자면... 간단히 설명하자면 @controller 붙은 클래스에서 발생한 예외를 잡아서 처리하겠다 이거임
그러면 Rest는 ResponstBody가 결합된거 확인가능 ! 응답객체를 리턴해줄려면 이걸 사용 단순 예외처리면 ControllerAdvice만 사용함*/
public class ApiExceptionHandler{
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiExceptionResponseDto handleValidationException(MethodArgumentNotValidException exception){
        log.warn(exception.getMessage());
        return new ApiExceptionResponseDto(exception.getFieldError().getDefaultMessage(), 400);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //상태코드를 400으로 집어넣어줌 .. https://velog.io/@davidko/API-Exception-%EC%B2%98%EB%A6%ACSpring 참고
    @ExceptionHandler(IllegalArgumentException.class)
    //EXceptionHandler이다 ()안에 특정 예외클래스를 지정해주면 컨트롤러단에서 해당 예외가 발생했을때 아래의 메소드를 수행하겠다 이말임
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
        return new ApiExceptionResponseDto(exception.getMessage(), 400);
    }


//    @ExceptionHandler(value = { IllegalArgumentException.class })
//    @ExceptionHandler({IllegalArgumentException.class, TokenMgrError.class})
//        log.warn(exception.getMessage());
//        return new ResponseEntity<>(
//    restApiException,
//    HttpStatus.BAD_REQUEST
//        );
//    찾는 내용이 없을때 하는 예외처리

    //회원가입시 vaild 규칙이 틀렸을때 이루어지는 예외처리
    //Controller단 이외에서 발생하는 예외처리는 advice로 처리되지 않는다.
    //다른말로 Controller단으로 오기전에or Controller단에서 시작하기전에 보내오는 메소드내에서 오류가나서
    //예외를 발생시켰을때 그부분에서 처리하고 끝나기 때문에 ControllerAdvice로는 잡아낼수없다.
//ApiExceptionHandler
    // protected 쓴 이유는 상속을받는 녀석 말고는 사용하지 않기 위해서
    //제 3의 방식 커스텀을 해서 예외발생을 커스텀으로 만들어줘서 처리한다.
    //커스텀할필요없이.. new IllegalArgumentException 으로 다 통일하는거랑 커스텀해서하는거랑 차이는없는데
    //나는 변경해서 예외처리를 해봤다 같은 느낌이다.
//    @ExceptionHandler(value = {CustomerException.class})  // 익셉션할 클래스 지정
//    protected ResponseEntity<ErrorResponse> handleCustomException(CustomerException ex) {
//        return ErrorResponse.toResponseEntity(ex.getErrorCode());
//    }
}

//반복되고 똑같은 작업들 ~ AOP(관심없고 반복되는 로직..) ...// 논란이 있다
//AOP냐 AOP가 아니냐 나는 AOP 한표