package com.sparta.team7_project.Persistence.repository;

import com.sparta.team7_project.entity.Post;
import com.sparta.team7_project.entity.PostLike;
import com.sparta.team7_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> deletePostLikeByUsername(String username);
    Optional<PostLike> findByPostIdAndUsername(Post post, User user);

}
