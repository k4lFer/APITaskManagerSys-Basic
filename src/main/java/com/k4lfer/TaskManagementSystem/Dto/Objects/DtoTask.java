package com.k4lfer.TaskManagementSystem.Dto.Objects;

import java.time.LocalDateTime;
import java.util.UUID;

import com.k4lfer.TaskManagementSystem.Dto.Other.StatusTask;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoTask {
    private UUID id;
    private String title;
    private String description;

    private StatusTask status;
    private LocalDateTime dueDate;

    private UUID userId;
    private UUID categoryId;
    private String categoryName;
    private UUID priorityId;
    private String priorityName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
