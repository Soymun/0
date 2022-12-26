package com.example.demo.Repositories;

import com.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserById(Long id);

    Optional<User> getUserByUsername(String username);

    List<User> findUserByGroupId(Long id);
}
