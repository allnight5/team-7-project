package com.sparta.team7_project.business.service;


import com.sparta.team7_project.Persistence.entity.Post;
import com.sparta.team7_project.presentation.dto.DeleteRequestDto;
import com.sparta.team7_project.presentation.dto.LoginRequestDto;
import com.sparta.team7_project.presentation.dto.MessageResponseDto;
import com.sparta.team7_project.presentation.dto.SignupRequestDto;
import com.sparta.team7_project.Persistence.entity.User;
import com.sparta.team7_project.enums.UserRoleEnum;
import com.sparta.team7_project.security.jwt.JwtUtil;
import com.sparta.team7_project.Persistence.repository.UserRepository;
import com.sun.jdi.request.DuplicateRequestException;
import io.jsonwebtoken.security.SecurityException;
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


    //    transaction - 데이터 베이스에서 데이터에 대한 하나의 논리적 실행단계
//      -> 모두 저장되거나 아무것도 저장되지 않거나를 보장
//    따라서 @Transactional이 붙은 곳에서 로직 실행 되다가 에러 발생 -> 메소드 요청전으로 롤백해줌  .rollback()
//    에러 없으면 그대로 데이터베이스에 반영  .commit()
//
//    옆에 readOnly를 적지 않으면 readOnly 기본값은 false
//    readOnly=true를 적으면 읽기전용이 됨
//      ->영속성 컨텍스트가 엔티티를 관리하지 않도록 하여 스냅샷을 생성하지 않아 메모리를 최적화할 수 있음
//          *스냅샷 : 영속성 컨텍스트는 엔티티를 저장할 때, 컬렉션으로 또 다른 복사본을 저장하게 되는데 이를 스냅샷이라 함
//                ->변경 감지가 일어났을 때 1차캐시에 있는 원본 객체가 중간에 변경되었는지 이 스냅샷으로 확인하게 됨
    @Transactional
    public MessageResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        //패스워드 암호화
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new DuplicateRequestException("중복된 사용자가 존재합니다.");
        }
        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        //어드민인지 유저인지 확인하는 조건문
        //다 통과하면 role이 admin으로 변경된다.
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new SecurityException("관리자 암호가 틀려 등록이 불가능합니다.");
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
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );
        // 비밀번호 확인

        //서버 저장 암호화된 비밀번호와 클라이언트가 입력한 패스워드를 암호화한 후 비교
        //같은 패스워드를 encode해도 salt값 때문에 매번 값이 달라지기에 equals로 구분 불가. 제공되는 matches함수 사용

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
        }
        return new MessageResponseDto(jwtUtil.createToken(user.getUsername(), user.getRole()));
    }
    //3.회원탈퇴
    @Transactional
    public MessageResponseDto delete(DeleteRequestDto deleteRequestDto, User user){
        //앞에는 지금 로그인한 유저가 게시글 작성한 유저와 같은지 검사함
        //뒤에는 지금이 로그인한사람이 유저인지 관리자인지 검사함
        if (user.getRole().equals(UserRoleEnum.ADMIN) || user.getUsername().equals(deleteRequestDto.getUsername())&&passwordEncoder.matches(deleteRequestDto.getPassword(),user.getPassword())) {
            //DB에서 삭제처리를 해줌
            userRepository.deleteByUsername(deleteRequestDto.getUsername());
            return new MessageResponseDto("삭제 성공", HttpStatus.OK.value());
        }
        throw new SecurityException("가입한 회원만이 탈퇴할 수 있습니다");
    }
}