package com.sparta.team7_project.Persistence.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POSTLIKE_ID")
    private Long id;

//    @Column
//    private Long Like_count;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID", nullable = false)
    private User username;

    @JoinColumn(name="POST_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Post postId;
    //    @ManyToOne(fetch = FetchType.LAZY)
//    private Post posts;
    @Column(nullable = false)
    private boolean status;


    //    @ManyToOne(fetch = FetchType.LAZY)
//    private User users;
    public PostLike(Post post, User user){
        this.postId = post;
        this.username = user;
        this.status = true;
    }
}