package com.example.SWAlab13.User.repository;

import com.example.SWAlab13.User.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}