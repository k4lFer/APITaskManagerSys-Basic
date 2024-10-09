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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Tasks")
public class Tasks {
    @Id
    private UUID id;

    // Relación con User
    @ManyToOne
    @JoinColumn(name = "userID") // Clave foránea para User
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusTask status;

    @Column(name = "dueDate")
    private LocalDateTime dueDate;


    @ManyToOne
    @JoinColumn(name = "categoryID") 
    private Category category;

    @ManyToOne
    @JoinColumn(name = "priorityID") 
    private Priority priority;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
