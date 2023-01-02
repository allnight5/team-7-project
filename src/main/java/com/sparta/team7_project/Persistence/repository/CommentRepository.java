package com.sparta.team7_project.Persistence.repository;

import com.sparta.team7_project.entity.Comment;
import com.sparta.team7_project.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    //작성한 순으로 위에 오게 정렬한다.
    //흠..? 안넣어 줘도 되는거 아닌가? 한번 빼고 넣고 비교해보자
    List<Comment> findAllByOrderByCreatedDateAsc();
    Optional<Comment> findById(Long id);
    List<Comment> findAllByPosts(Post post);
}
