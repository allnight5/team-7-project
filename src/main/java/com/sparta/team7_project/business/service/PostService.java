package com.sparta.team7_project.business.service;

import com.sparta.team7_project.business.dto.CommentResponseDto;
import com.sparta.team7_project.business.dto.PostRequestDto;
import com.sparta.team7_project.business.dto.PostResponseDto;
import com.sparta.team7_project.dto.*;
import com.sparta.team7_project.Persistence.entity.Comment;
import com.sparta.team7_project.Persistence.entity.Post;
import com.sparta.team7_project.Persistence.entity.User;
import com.sparta.team7_project.enums.UserRoleEnum;
import com.sparta.team7_project.Persistence.repository.CommentRepository;
import com.sparta.team7_project.Persistence.repository.PostRepository;
import com.sparta.team7_project.presentation.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostLikeService postLikeService;

    //1.게시글 생성
    @Transactional
    public ResponseDto<PostResponseDto> createPost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto, user.getUsername(), user);
        postRepository.save(post);
        return new ResponseDto<>(new PostResponseDto(post));
    }
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        List<Post> data = postRepository.findAllByOrderByModifiedDateDesc();
        //댓글을 포함한 여러개의 게시글을 담아줄 리스트
        //한개만 보내줄거면 필요없음.
        List<PostResponseDto> result = new ArrayList<>();
        //게시글의 갯수만큼 확인하고 담아주기위해서 반복문을 돌림
        for (Post post : data){
            //이게 JPA로 양방향 연결이 post로 되어있어서
            //이 포스트와 연결된 댓글(comment)만 comments라는 리스트 멤버변수에 넣어줌
            List<Comment> comments = commentRepository.findAllByPosts(post);
            //읽어 들인 post를 Dto형태로 형변환 해줌
            PostResponseDto postResponseDto = new PostResponseDto(post);
            //이제 이하나의 포스트에 연관된 댓글을 담아줄 List를 만들어줌
            List<CommentResponseDto> commentResponseDto = new ArrayList<>();
            //리스트를 만들었다면 이제 그 리스트에 연관된 댓글을 담아야 하니
            //연관된 숫자만큼 반복문을 돌려줌
            for(Comment comment : comments){
                //만든 리스트 변수에 댓글들을 Dto형태로 위에 만든 리스트 변수에 넣어줌
                commentResponseDto.add(new CommentResponseDto(comment));
            }
            //이제 Dto리스트에 연관된 댓글을 담은 댓글리스트를 넣어줌
            postResponseDto.addCommentList(commentResponseDto);
            //이 포스트와 연관된 댓글을 넣은 게시글에 댓글이달린
            // Dto완성채를 게시글 전체를 담은 리스트에 넣어주고
            //다음 게시글에 연관된 댓글을 넣어주러 반복문 확인을하러감
            result.add(postResponseDto);
        }
        //리스트를 되돌려서 보내줌
        return result;
    }

    //3.선택한 게시글 조회
    @Transactional
    public ResponseDto<PostResponseDto> getPost(Long id) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 삭제되었습니다.")
        );
        //이게 JPA로 양방향 연결이 post로 되어있어서
        //이 포스트와 연결된 댓글(comment)만 comments라는 리스트 멤버변수에 넣어줌
        List<Comment> comments = commentRepository.findAllByPosts(post);
        //읽어 들인 post를 Dto형태로 형변환 해줌
        PostResponseDto postResponseDto = new PostResponseDto(post);
        //이제 이하나의 포스트에 연관된 댓글을 담아줄 List를 만들어줌
        List<CommentResponseDto> commentResponseDto = new ArrayList<>();
        //리스트를 만들었다면 이제 그 리스트에 연관된 댓글을 담아야 하니
        //연관된 숫자만큼 반복문을 돌려줌
        for(Comment comment : comments){
            //만든 리스트 변수에 댓글들을 Dto형태로 위에 만든 리스트 변수에 넣어줌
            commentResponseDto.add(new CommentResponseDto(comment));
        }
        //이제 Dto리스트에 연관된 댓글을 담은 댓글리스트를 넣어줌
        postResponseDto.addCommentList(commentResponseDto);
        //게시글을 되돌려서 보내줌
        return new ResponseDto<>(postResponseDto);
    }
    //4.선택한 게시글 수정
    @Transactional
    public MessageResponseDto update(Long id, PostRequestDto requestDto, User user){
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("이미 삭제된 게시글입니다.")
        );
        //앞에는 지금 로그인한 유저가 게시글 작성한 유저와 같은지 검사함
        //뒤에는 지금이 로그인한사람이 유저인지 관리자인지 검사함
        if(user.getUsername().equals(post.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            //포스트를 받은 데이터로 업데이트해줌
            post.update(requestDto);
            return new MessageResponseDto("수정 성공", HttpStatus.OK.value());
        }
        return new MessageResponseDto("수정 실패.", 400);

    }

    //5.선택한 게시글 삭제
    @Transactional
    public MessageResponseDto delete(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("이미 삭제된 게시글입니다.")
        );
        //앞에는 지금 로그인한 유저가 게시글 작성한 유저와 같은지 검사함
        //뒤에는 지금이 로그인한사람이 유저인지 관리자인지 검사함
        if (user.getUsername().equals(post.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            //DB에서 삭제처리를 해줌
            postRepository.delete(post);
            return new MessageResponseDto("삭제 성공", HttpStatus.OK.value());
        }
        return new MessageResponseDto("삭제 실패", HttpStatus.FAILED_DEPENDENCY.value());
    }


    @Transactional
    public MessageResponseDto updateLikePost(Long id, User user){
        Post post = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        if(!postLikeService.hasLikePost(post, user)) {
            post.increaseLikeCount();
            return postLikeService.createLikePost(post, user);
        }
        post.decreaseLikeCount();
        return postLikeService.removeLikePost(post, user);
    }

}