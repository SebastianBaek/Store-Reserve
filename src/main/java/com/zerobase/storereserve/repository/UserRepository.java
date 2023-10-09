package com.zerobase.storereserve.repository;

import com.zerobase.storereserve.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String name);

    Optional<User> findByUsername(String username);
}
