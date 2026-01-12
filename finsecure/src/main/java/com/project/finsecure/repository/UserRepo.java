package com.project.finsecure.repository;


import com.project.finsecure.entity.Users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}
