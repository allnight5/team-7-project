package com.sparta.team7_project.presentation.controller;


import com.sparta.team7_project.business.dto.CommentRequestDto;
import com.sparta.team7_project.business.dto.CommentResponseDto;
import com.sparta.team7_project.business.service.CommentLikeService;
import com.sparta.team7_project.dto.*;
import com.sparta.team7_project.presentation.dto.MessageResponseDto;
import com.sparta.team7_project.security.service.UserDetailsImpl;
import com.sparta.team7_project.business.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController//CONTROLLER VS RESTCONTROLLER 두개의 차이점
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentLikeService commentLikeService;

    //1. 댓글 생성 API
    @ResponseBody
    @PostMapping("/{id}/comment")
    public Object createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        return commentService.createComment(requestDto, userDetails.getUser(), id);
    }
    //2.댓글 수정 API
    @ResponseBody
    @PutMapping("/{id}/comment")
    public MessageResponseDto updateComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        return commentService.updateComment(requestDto, userDetails.getUser(), id);
    }
    //3. 댓글 삭제 API
    @ResponseBody
    @DeleteMapping("/{id}/comment")
    public MessageResponseDto deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        return commentService.deleteComment(userDetails.getUser(), id);
    }
    //4.댓글 좋아요 카운트
    @PostMapping("/{id}/comment/{commentId}/like")
    public MessageResponseDto likeComment(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable Long commentId){
        return commentLikeService.updateAndCountCommentLike(userDetails.getUser(),commentId);
    }

  /* @GetMapping("/{id}/comment/{commentId}/like")
    public void seeLikeComment(@PathVariable Long commentId){
        commentLikeService.LikeCount(commentId);
    }
*/
}
