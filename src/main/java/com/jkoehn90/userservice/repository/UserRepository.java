package com.jkoehn90.userservice.repository;

import com.jkoehn90.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByEmail(String email); //find email, Optional<User> handles if no user is found
    boolean existsByEmail(String email); //check if email already exists
}
