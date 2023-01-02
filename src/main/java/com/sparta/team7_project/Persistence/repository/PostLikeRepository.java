package com.sparta.team7_project.Persistence.repository;

import com.sparta.team7_project.Persistence.entity.Post;
import com.sparta.team7_project.Persistence.entity.PostLike;
import com.sparta.team7_project.Persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostIdAndUsername(Post post, User user);

}
