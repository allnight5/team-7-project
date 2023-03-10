package com.sparta.team7_project.Persistence.repository;

import com.sparta.team7_project.Persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> deleteByUsername(String username);
//    Optional<User> findByEmail(String email);
}
