package com.sparta.team7_project.business.service;

import com.sparta.team7_project.Persistence.entity.Comment;
import com.sparta.team7_project.Persistence.entity.CommentLike;
import com.sparta.team7_project.Persistence.repository.CommentLikeRepository;
import com.sparta.team7_project.Persistence.repository.CommentRepository;
import com.sparta.team7_project.presentation.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sparta.team7_project.Persistence.entity.User;

import java.util.List;



@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional//좋아요 판별하는 부분
    public MessageResponseDto updateAndCountCommentLike(User user, Long commentId, Long id){
        if(commentLikeRepository.findAllByCommentIdAndUsername(commentId ,user.getUsername()).isEmpty()){
            CommentLike commentLike = new CommentLike(user,commentId, id);
            commentLikeRepository.save(commentLike);

            //... 위랑 아래는 별개의 기능..

            Comment comment = commentRepository.findById(commentId).orElseThrow(
                    ()-> new IllegalArgumentException("댓글없음")
            );
            //코멘트 객체를 사용하기 위해서 왜 코멘트 객체를 사용해야 하냐면 코멘트 엔티티에 좋아요갯수샌거를 추가해주기 위해서

            comment.LikeCount(LikeCount(commentId));

            return new MessageResponseDto("좋아요 하나 증가", HttpStatus.OK.value());

        }else {//유저네임+댓글id 일치하는게 있을경우
            commentLikeRepository.deleteCommentLikeByUsernameAndCommentId(user.getUsername(), commentId);
            //
            Comment comment = commentRepository.findById(commentId).orElseThrow(
                    ()-> new IllegalArgumentException("댓글없음")
            );
            comment.LikeCount(LikeCount(commentId));
            return new MessageResponseDto("좋아요 취소", HttpStatus.OK.value());
        }
    }

    //좋아요 갯수 새는 부분
    public Long LikeCount(Long commentId){

      List<CommentLike> countLikeList =  commentLikeRepository.findAllByCommentId(commentId);
        return (long) countLikeList.size();
    } //이제 이건 숫자를 반환하는겨 ... 근데 이숫자가 댓글안으로 들어가야함... ...흠..

}
