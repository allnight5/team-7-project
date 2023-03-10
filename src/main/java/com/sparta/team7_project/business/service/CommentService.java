package com.sparta.team7_project.business.service;


import com.sparta.team7_project.Persistence.entity.CommentLike;
import com.sparta.team7_project.Persistence.repository.CommentLikeRepository;
import com.sparta.team7_project.business.dto.CommentRequestDto;
import com.sparta.team7_project.business.dto.CommentResponseDto;
import com.sparta.team7_project.dto.*;
import com.sparta.team7_project.Persistence.entity.Comment;
import com.sparta.team7_project.Persistence.entity.Post;
import com.sparta.team7_project.Persistence.entity.User;
import com.sparta.team7_project.enums.UserRoleEnum;
import com.sparta.team7_project.security.jwt.JwtUtil;
import com.sparta.team7_project.Persistence.repository.CommentRepository;
import com.sparta.team7_project.Persistence.repository.PostRepository;
import com.sparta.team7_project.Persistence.repository.UserRepository;
import com.sparta.team7_project.presentation.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    /*
        두개의 저장소(Post, User) Repository 가 필요한 이유.. 어떤 게시글인지 알고 연결해야하고
        현재 사용자가 권한이 있는지도 알아야하니 연관된 두가지 저장소가 필요한다.
    */
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    //1.댓글 생성
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, User user, Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("이미 삭제된 게시글입니다.")
        );
        Comment comment = new Comment(requestDto, user.getUsername());
        comment.addUserAndPost(user, post);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    //중복코드를 메소드분리 시키고 싶다면 중복코드부분만 드래그해서
    //1. 마우스 우측클릭 -> Refactor -> Extarct Mathod
    //2. 단축키로는 windows기준 ctrl+alt+M 누르면됩니다.

    //2.댓글 수정
    @Transactional
    public MessageResponseDto updateComment(CommentRequestDto requestDto, User user, Long id) {
        Comment comment = getComment(id);
        if (user.getRole() == UserRoleEnum.ADMIN || comment.isWriter(user.getUsername())) {
            comment.update(requestDto);
            MessageResponseDto msg = new MessageResponseDto("업데이트 성공", HttpStatus.OK.value());
            return msg;
        }
        MessageResponseDto msg = new MessageResponseDto("수정 실패(권한이 없습니다)", 400);
        return msg;
    }

    //3.댓글 삭제
    @Transactional
    public MessageResponseDto deleteComment(User user, Long id){
        Comment comment = getComment(id);
        if (comment.isWriter(user.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)){
            commentRepository.deleteById(comment.getId());
            MessageResponseDto msg = new MessageResponseDto("삭제 성공", HttpStatus.OK.value());
            commentLikeRepository.deleteCommentLikeByUsernameAndCommentId(user.getUsername(), id);
            return msg;
        }
        MessageResponseDto msg = new MessageResponseDto("삭제 실패(권한이 없습니다)", 400);
        return msg;
    }

    //조회분리
    private Comment getComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 삭제되어 존재하지 않습니다.")
        );
        return comment;
    }
}
