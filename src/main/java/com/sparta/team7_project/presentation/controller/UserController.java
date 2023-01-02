package com.sparta.team7_project.presentation.controller;

import com.sparta.team7_project.presentation.dto.LoginRequestDto;
import com.sparta.team7_project.presentation.dto.MessageResponseDto;
import com.sparta.team7_project.dto.SecurityExceptionDto;
import com.sparta.team7_project.presentation.dto.SignupRequestDto;
import com.sparta.team7_project.security.jwt.JwtUtil;
import com.sparta.team7_project.business.service.UserService;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController//CONTROLLER VS RESTCONTROLLER 두개의 차이점
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //1.회웝가입

    @PostMapping("/signup")
    public MessageResponseDto signupPage(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        MessageResponseDto msg =userService.signup(signupRequestDto);
        return msg;

    }
    //2.로그인
    @PostMapping("/login")
    public MessageResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        //이름과 유저인지 관리자인지 구분한 토큰을 가져오는 부분
        MessageResponseDto msg = userService.login(loginRequestDto);
        //문자열 token에 가져온 정보를 넣어주는 부분
        String token = msg.getMessage();
        //헤더를 통해 토큰을 발급해 주는 부분
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        //400은 실패기 때문에 실패한 이유 메시지와 상태코드를 가져온다.
        if(msg.getStatusCode() == 400){
            //MesaagerResponseDto형태로 보내주기 실패의 경우 깨진다..
            //그래서 ResponseEntity로 묶어서 보내주었다.
            return new MessageResponseDto(msg.getMessage(), msg.getStatusCode());
        }
        //로그인 성공했으니 로그인을 성공했다는 메시지와 상태 200코드를 보내준다.
        else {
            return new MessageResponseDto("로그인 되었습니다.", 200);
        }
    }

    @GetMapping("/forbidden")
    public MessageResponseDto forbidden(){
        return new MessageResponseDto("권한이 없습니다.", 400);
    }
}