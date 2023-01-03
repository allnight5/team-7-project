package com.sparta.team7_project.Persistence.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//
//    @Column
//    private Long Like_count;

    @Column
    private String username;
    @Column
    private Long commentId;

/*    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comments;*/
    @ManyToOne(fetch = FetchType.LAZY)
    private User users;

    public CommentLike(User user , Long commentId) {
        this.commentId= commentId;
        this.users = user;
        this.username = user.getUsername();
    }
}
