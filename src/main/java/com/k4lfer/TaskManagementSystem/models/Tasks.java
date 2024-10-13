package com.k4lfer.TaskManagementSystem.models;

import java.time.LocalDateTime;
import java.util.UUID;

import com.k4lfer.TaskManagementSystem.Dto.Other.StatusTask;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Tasks")
public class Tasks {
    @Id
    private UUID id;

    @Column(name = "title")
    private String title;

    @Setter
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusTask status;

    @Column(name = "dueDate")
    private LocalDateTime dueDate;

        @ManyToOne
        @JoinColumn(name = "userId") 
        private User user;

        @ManyToOne
        @JoinColumn(name = "categoryId") 
        private Category category;

        @ManyToOne
        @JoinColumn(name = "priorityId") 
        private Priority priority;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
