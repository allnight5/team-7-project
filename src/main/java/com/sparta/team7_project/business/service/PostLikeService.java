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

    public MessageResponseDto createLikePost(Post post, User user){
        PostLike postlike = new PostLike(post, user);
        postLikeRepository.save(postlike);
        return new MessageResponseDto("게시글 좋아요 성공", HttpStatus.OK.value());
    }
    public MessageResponseDto removeLikePost(Post post, User user){
        PostLike postLike = postLikeRepository.findByPostIdAndUsername(post, user).orElseThrow(() -> {
            throw new IllegalArgumentException("좋아요가 없습니다.");
        });
        postLikeRepository.delete(postLike);
        return new MessageResponseDto("게시글 좋아요 취소 성공", HttpStatus.OK.value());
    }
    public boolean hasLikePost(Post post, User user){
        return postLikeRepository.findByPostIdAndUsername(post, user).isPresent();
    }
}
