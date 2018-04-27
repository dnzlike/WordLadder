package com.example.wordladder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wordladder.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}