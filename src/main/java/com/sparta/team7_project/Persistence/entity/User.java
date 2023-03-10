package com.sparta.team7_project.Persistence.entity;

import com.sparta.team7_project.enums.UserRoleEnum;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "users유저")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID컬럼명")  //jpa는 똑똑하다  ..망상
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    //    @Column(nullable = false, unique = true)
//    private String email;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "users" , cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();
    @OneToMany(mappedBy = "users" , cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "username" , cascade = CascadeType.REMOVE)
    private List<PostLike> postLike = new ArrayList<>();

    @OneToMany(mappedBy = "users" , cascade = CascadeType.REMOVE)
    private List<CommentLike> commentLike = new ArrayList<>();

    public void addPost(Post post){
        post.setUsers(this);
        this.posts.add(post);
    }

    public User(String username, String password,UserRoleEnum role) {
        this.username = username;
        this.password = password;
//        this.email = email;
        this.role = role;
    }
}