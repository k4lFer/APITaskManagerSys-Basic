package com.k4lfer.TaskManagementSystem.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.k4lfer.TaskManagementSystem.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,UUID>{
    //Optional<User> findById(UUID id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email); 
}
