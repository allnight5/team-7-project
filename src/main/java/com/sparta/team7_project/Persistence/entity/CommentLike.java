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
    @Column(name = "CommentLike_Id")
    private Long id;
//
//    @Column
//    private Long Like_count;

    @Column
    private String username;
    @Column
    private Long commentId;
    @Column
    private Long postId;

/*    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comments;*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID") //조인컬럼찾을때 필드명_참조테이블컬럼명
    private User users;

    public CommentLike(User user , Long commentId, Long id) {
        this.commentId= commentId;
        this.users = user;
        this.username = user.getUsername();
        this.postId=id;
    }
}
