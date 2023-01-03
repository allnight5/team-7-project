package com.sparta.team7_project.business.dto;

import com.sparta.team7_project.Persistence.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDto{
    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int postlikeCount;
    private List<CommentResponseDto> commentList;
    public PostResponseDto (Post post) {
        this.id = post.getId();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
        this.title = post.getTitle();
        this.username = post.getUsername();
        this.content = post.getContent();
        this.postlikeCount = post.getLikeCount();
    }
    public PostResponseDto (Post post, List<CommentResponseDto> comment) {
        this.id = post.getId();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUsername();
        this.postlikeCount = post.getLikeCount();
        this.commentList = comment;
    }

    public void addCommentList(List<CommentResponseDto> commentList) {
        this.commentList = commentList;
    }
}