package com.k4lfer.TaskManagementSystem.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.k4lfer.TaskManagementSystem.Dto.Other.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name="Users")
public class User {
    @Id
    @Column(name="id")
    private UUID id;

    @Column(name="username")
    private String username;

   // @Column(name="imageProfile")
   // private String imageProfile;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role;

    @Column(name="created_at")
    private LocalDateTime created_at;

    @Column(name="updated_at")
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "user")
    private List<Tasks> tasks;
}
