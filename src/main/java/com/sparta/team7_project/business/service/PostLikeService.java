package com.sparta.team7_project.business.service;


import com.sparta.team7_project.Persistence.repository.CommentRepository;
import com.sparta.team7_project.Persistence.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    //좋아요 증가

}
