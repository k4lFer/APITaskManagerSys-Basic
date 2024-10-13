package com.k4lfer.TaskManagementSystem.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="Priorities")
public class Priority {
    @Id
    private UUID id;
    
    @Column(name="name")
    private String name;

    @Column(name="level")
    private Integer level;

    @Column(name="created_at")
    private LocalDateTime created_at;
    
    @Column(name="updated_at")
    private LocalDateTime updated_at;

        @OneToMany(mappedBy = "priority")
        private List<Tasks> tasks;
}
