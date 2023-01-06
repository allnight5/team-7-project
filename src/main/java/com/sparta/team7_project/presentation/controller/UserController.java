package com.sparta.team7_project.presentation.controller;

import com.sparta.team7_project.presentation.dto.DeleteRequestDto;
import com.sparta.team7_project.presentation.dto.LoginRequestDto;
import com.sparta.team7_project.presentation.dto.MessageResponseDto;
import com.sparta.team7_project.presentation.dto.SignupRequestDto;
import com.sparta.team7_project.security.jwt.JwtUtil;
import com.sparta.team7_project.business.service.UserService;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.sparta.team7_project.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


//@Controller -> view 반환.. 따라서 객체와 같은 데이터를 반환하려면 @ReponseBody를 붙혀줘서 데이터를 감싸서 반환해야함

//@ResponseBody -> 컨트롤러가 데이터를 반환할 때 HttpMessageConverter를 사용해서 자바 객체에 응답 본문(body) 메시지를 만들어 반환할 수 있게 도와준다.

//이렇게 @ResponseBody 계속 붙히면 번거로우니 나온게 @RestController
//@Restcontroller는 json 등의 형태로 객체 데이터를 반환해줌. 동작과정은 Controller에 @RequestBody 붙힌 거랑 완전 동일
//즉 @RestController은 @Controller와 @ResponseBody의 조합


@RestController


//메서드 혹은 클래스 단위로 Mapping을 주어 중복 URL을 공통으로 처리할 수 있다

@RequestMapping("/api/user")

// final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
@RequiredArgsConstructor
public class UserController {

    //의존성 주입
    private final UserService userService;

    //1.회웝가입

    //POST 메소드는 주로 새로운 리소스를 생성(create)할 때 사용. 서버에 데이터 추가 or 작성시
    @PostMapping("/signup")

    //@RequestBody -> 클라이언트에서 JSON 데이터를 요청 본문에 담아서 서버로 보내면,
    //서버에서는 @RequestBody 어노테이션을 사용하여 HTTP 요청 본문에 담긴 값들을 자바객체로 변환시켜, 객체에 저장한다.
    //@Valid
    //->@RequestBody 어노테이션 옆에 @Valid를 작성하면, RequestBody로 들어오는 객체에 대한 검증을 수행한다.
    // 이 검증의 세부적인 사항은 객체 안에 정의를 해두어야 한다.ex)정규표현식
    public MessageResponseDto signupPage(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        MessageResponseDto msg =userService.signup(signupRequestDto);
        return msg;

    }
//    @ResponseBody
//    @PostMapping("/login")
//    public MsgResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
//        userService.login(loginRequestDto, response);
//
//        return new MsgResponseDto("로그인 성공", HttpStatus.OK.value());
//    }
    //2.로그인
    @PostMapping("/login")
    public MessageResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        //이름과 유저인지 관리자인지 구분한 토큰을 가져오는 부분
        MessageResponseDto msg = userService.login(loginRequestDto);
        //문자열 token에 가져온 정보를 넣어주는 부분
        String token = msg.getMessage();
        //헤더를 통해 토큰을 발급해 주는 부분
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        return new MessageResponseDto("로그인 되었습니다.", 200);
    }
    //3.유저 삭제
    @DeleteMapping("/delete")
    //@AuthenticationPrincipal -> 세션 정보 UserDetails에 접근할 수 있는 어노테이션
    //현재 로그인한 사용자 객체를 가져오기 위해 필요
    public MessageResponseDto delete(@RequestBody DeleteRequestDto deleteRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.delete(deleteRequestDto, userDetails.getUser());
    }
}