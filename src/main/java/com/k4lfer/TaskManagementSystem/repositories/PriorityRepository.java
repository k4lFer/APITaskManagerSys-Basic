package com.k4lfer.TaskManagementSystem.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.k4lfer.TaskManagementSystem.models.Priority;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, UUID>{

}
