package com.pft.repository;

import com.pft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u.name FROM users u WHERE u.email = :email", nativeQuery = true)
    String findName(String email);
}
