package com.watchez.WatchEz.repository;

import com.watchez.WatchEz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsernameOrEmail(String username, String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
}
