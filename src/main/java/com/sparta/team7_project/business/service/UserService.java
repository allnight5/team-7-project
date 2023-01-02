package com.sparta.team7_project.business.service;


import com.sparta.team7_project.presentation.dto.LoginRequestDto;
import com.sparta.team7_project.presentation.dto.MessageResponseDto;
import com.sparta.team7_project.presentation.dto.SignupRequestDto;
import com.sparta.team7_project.entity.User;
import com.sparta.team7_project.enums.UserRoleEnum;
import com.sparta.team7_project.security.jwt.JwtUtil;
import com.sparta.team7_project.Persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "DeXi341@dNDI";

    //1.회원가입
    @Transactional
    public MessageResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
//        String email = signupRequestDto.getEmail();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            return new MessageResponseDto("중복된 사용자가 존재합니다.", 400);
        }        // 회원 중복 확인
//        Optional<User> emailFound = userRepository.findByEmail(email);
//        if (emailFound .isPresent()) {
//            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
//        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        //어드민인지 유저인지 확인하는 조건문
        //다 통과하면 role이 admin으로 변경된다.
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                return new MessageResponseDto("관리자 암호가 틀려 등록이 불가능합니다.", 400);
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);
        return new MessageResponseDto("회원 가입 완료", HttpStatus.OK.value());
    }

    //2.로그인
    @Transactional(readOnly = true)
    public MessageResponseDto login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다. 회원가입 해주시기 바랍니다.")
        );
//        Optional<User> user = userRepository.findByUsername(username);
//        if(user.isEmpty()){
//            return new MessageResponseDto("사용자가 존재하지 않습니다.", 400);
//        }
        // 비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())){
            return new MessageResponseDto("비밀번호가 틀렸습니다.", 400);
        }
//        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.get().getUsername(), user.get().getRole()));
        return new MessageResponseDto(jwtUtil.createToken(user.getUsername(), user.getRole()));
    }
}