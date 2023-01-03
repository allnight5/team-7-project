package com.sparta.team7_project.Persistence.repository;

import com.sparta.team7_project.Persistence.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> deleteCommentLikeByUsernameAndCommentId  (String username,Long commentId);
// 내가 원하지 않는 댓글의 좋아요도 삭제됨...
    Optional<CommentLike> findAllByCommentIdAndUsername(Long commentId,String username);

    List<CommentLike> findAllByCommentId(Long commentId);


    //그럼 일단 코멘트라이크에 유저네임을 받아와야함 그러면 유저...를 댓글라이크가 가지고 있어야함
    //댓글라이크가 유저를 컬럼으로 가지고 있으면 .. 유저.getName 이렇게 하면 유저네임을 사용용가능



    //일단 저장하고 그러면 머가 필요함? >>엔티티
    // 에 댓글아이디 ,유저는 객체로 ,구분을위한 유저네임,  관계생성//정도 저장하자
    //>>이러면 저장할때는 유저객체를 생성해서 댓글라이크에/getUserName을 넣어..

    //근데 궁금한거 하나 있지 ..그러면 저장하기 전에 조회를 해야함 .
    //댓글라이크 레포 긁어서  뭘로 ? 댓글 id and 유저네임
    // 이때 댓글id에~ 유저네임이 존재하면..삭제해버림
    //그리고...
    //음 ? 댓글 아이디는 어디서 가져와야해 ? ... 패스베리어블로 가져워야지뭐...
    //  >>좋아요 겟수는 댓글아이디로 긁어와서 리스트 사이즈를 반환할꺼임
    // 이미 들어왔다면 인증된 사람이자나 그러면 http토큰을 이용해서 ..? 아이디 획득 ~ ?



    //관계를 맺는다면

    //댓글라이크에는... 유저와 관계를 맺고 싶은데 ... 음.. 매니투 매니?
    //그러면 댓글좋아요와 유저의 관계는 단방향으로 매니튜 원
    //
    //
    //댓글과 댓글 좋아요의 관계는 일방적인 관계이다 .. 댓글은 댓글좋아요가 필요없다
    //댓글좋아요는 댓글이 잇어야만 존재가능 이것은 ... 매니투원이다
}
