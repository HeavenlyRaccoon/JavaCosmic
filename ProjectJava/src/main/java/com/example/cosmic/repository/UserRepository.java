package com.example.cosmic.repository;

import com.example.cosmic.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);

    User findById(int id);
}
