package com.k4lfer.TaskManagementSystem.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.k4lfer.TaskManagementSystem.models.Tasks;

public interface TaskRepository extends JpaRepository<Tasks, UUID>{
    Page<Tasks> findByUserId(UUID userId, Pageable pageable);
    Page<Tasks> findByUserIdAndCategoryId(UUID userId, UUID categoryId, Pageable pageable);
    Page<Tasks> findByUserIdAndPriorityId(UUID userId, UUID priorityId, Pageable pageable);
    Page<Tasks> findByUserIdAndCategoryIdAndPriorityId(UUID userId, UUID categoryId, UUID priorityId, Pageable pageable);

}
