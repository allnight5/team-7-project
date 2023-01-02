package com.sparta.team7_project.dto;

import com.sparta.team7_project.Persistence.entity.User;
import com.sparta.team7_project.business.dto.CommentResponseDto;
import com.sparta.team7_project.business.dto.PostResponseDto;
import com.sparta.team7_project.enums.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private String password;
    private UserRoleEnum role;
    private List<PostResponseDto> posts;
    private List<CommentResponseDto> commentList;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
    }
    public void addPostList(List<PostResponseDto> postList) {
        this.posts = postList;
    }
    public void addCommentList(List<CommentResponseDto> commentList) {
        this.commentList = commentList;
    }
}
