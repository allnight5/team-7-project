package com.sparta.team7_project.Persistence.repository;
import com.sparta.team7_project.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface PostRepository extends JpaRepository<Post, Long> {

    // List 에 모든 게시글을 담아서 가져온다.
    List<Post> findAllByOrderByModifiedDateDesc();

    // 게시글 하나만 조회하는 기능
    Optional<Post> findById(Long Id);


}