package com.sparta.team7_project.business.service;

import com.sparta.team7_project.Persistence.entity.Post;
import com.sparta.team7_project.Persistence.entity.PostLike;
import com.sparta.team7_project.Persistence.entity.User;
import com.sparta.team7_project.Persistence.repository.PostLikeRepository;
import com.sparta.team7_project.presentation.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    //게시글 좋아요 증가
    public MessageResponseDto createLikePost(Post post, User user){
        PostLike postlike = new PostLike(post, user);
        postLikeRepository.save(postlike);
        return new MessageResponseDto("게시글 좋아요 성공", HttpStatus.OK.value());
    }

    //게시글 좋아요 삭제
    public MessageResponseDto removeLikePost(Post post, User user){
        PostLike postLike = postLikeRepository.findByPostIdAndUsername(post, user).orElseThrow(() -> {
            throw new IllegalArgumentException("게시글이 없습니다..");
        });
        postLikeRepository.delete(postLike);
        return new MessageResponseDto("게시글 좋아요 취소 성공", HttpStatus.OK.value());
    }
    //게시글에 유저가 작성한 좋아요가 있는지 확인
    public boolean hasLikePost(Post post, User user){
        return postLikeRepository.findByPostIdAndUsername(post, user).isPresent();
    }
}
