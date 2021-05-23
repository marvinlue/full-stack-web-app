package com.example.project3.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {

    // SELECT * FROM users WHERE username = ?
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserById(Long id);
    Optional<User> findByIdAndUsername(Long id, String username);
}