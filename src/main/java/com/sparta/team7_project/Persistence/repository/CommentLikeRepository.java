package com.sparta.team7_project.Persistence.repository;

import com.sparta.team7_project.Persistence.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> deleteCommentLikeByUsername(String username);
}
