package com.sparta.team7_project.presentation.controller;

import com.sparta.team7_project.business.dto.PostRequestDto;
import com.sparta.team7_project.business.dto.PostResponseDto;
import com.sparta.team7_project.security.jwt.JwtUtil;
import com.sparta.team7_project.presentation.dto.MessageResponseDto;
import com.sparta.team7_project.security.service.UserDetailsImpl;
import com.sparta.team7_project.business.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //@ResponseBody(데이터를 반환해야 하는 경우 Json 형태로 반환) + @Controller(Spring MVC의 컨트롤러, Model 객체를 만들어 데이터를 담고 View를 반환
@RequiredArgsConstructor //의존성주입으로 사용, final을 가지고 있는 속성에 롬복에의해 자동으로 생성자를 만들어줌
@RequestMapping("/api") // 중복 매핑
public class PostController {
    //jwtUtil에 comment어느테이션을 넣어준이유는
    //Service에서 사용하기 위해서이다.
    private final PostService postService;
    private final JwtUtil jwtUtil;

    //1. 게시글 생성 API
    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        /* 클라이언트 -> 서버 통신 : Request, 서버-> 클라이언트 통신 : Response.
        새로고침 없이 이루어지는 통신 : 대부분 비동기 통신(클라->서버 요청시 본문에 데이터(JSON) 담아서, 서버->클라 응답시 본문에 데이터 담아서.
        이 본문들이 RequestBody, ResponseBody.
        @RequestBody, 클라가 전송하는 JSON 형태의 HTTP Body 내용을 Java 객체로 변화시켜줌. so, body가 없는 get, delete 등 메소드에 @Requestbody 쓰면 에러 발생
        */
        /*
            @AuthenticationPrincipal어노테이션이 하는역할
            스프링 시큐리티가 제공해주는 기능 중에 하나로 스프링 MVC 핸들러 파라메터에 @AuthenticationPrincipal를
            사용하면 getPrincipal() 로 리턴 받을 수 있는 객체를 바로 주입받을 수가 있다고 한다.
            있는 지 없는지만 확인하는 용도로 사용
            인증 안한 경우에 null
            인증 한 경우에는 username과 authorities 참조 가능
         */
        return postService.createPost(requestDto, userDetails.getUser());
    }

    /* @GetMapping과 @PostMapping 어노테이션은 @PutMapping, @DeleteMapping, @PatchMapping과 함께 스프링 4.3부터 등장했다. 왜 @RequestMapping 대신 @PostMapping 등등을 쓸까 ?
    --> 1.코드가 줄어듬 2.url중복 사용가능 3.코드에 의미명시(GET 화면에 뿌릴떄, POST 전송 데이터 insert 할 때 등등  ex.@RequestMapping(value="경로", method=RequestMethod.GET) -> @GetMapping("경로") */
    /* @RequestParam : 요청 파라미터를 메소드 파라미터에 넣어줌  ex. /board/detail?boardId=${boardId} **사용시, 파라미터 넘기지 않으면(null)시에 400에러 ! 그래서 꼭 required = false 넣어야 함
    @PathVariable : @RequestMapping의 URL에서의 ({})의 명시된 변수를 받아온다. + @PathVariable을 이용해서 URI 템플릿 중에서 어떤 파라미터를 가져올 지 결정 가능 ex. /board/detail/${boardId} */


    // 2. 게시글 전체 목록 조회 API
    @GetMapping("/post/get") //화면에 데이터 뿌릴께
    public List<PostResponseDto> getPostList() {   //PostResponseDto 엔티티를 참조한 getPostList

        return postService.getPosts(); //PostService의 getPosts 메서드 리턴
    }

    // 3. 선택한 게시글 조회 API
    @GetMapping("/post/get/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    //4. 선택한 게시글 수정 API
    @PutMapping("/post/{id}")
    public MessageResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.update(id, requestDto, userDetails.getUser());
    }

    //5. 선택한 게시글 삭제 API
    @DeleteMapping("/post/{id}")
    public MessageResponseDto deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.delete(id, userDetails.getUser());
    }
    //6.게시글 좋아요 추가와 취소
    @PostMapping("/post/{id}/like")
    public MessageResponseDto likePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.updateLikePost(id, userDetails.getUser());
    }

    //
}