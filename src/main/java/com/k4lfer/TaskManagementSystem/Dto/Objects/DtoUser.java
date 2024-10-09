package com.k4lfer.TaskManagementSystem.Dto.Objects;

import java.time.LocalDateTime;
import java.util.UUID;


import com.k4lfer.TaskManagementSystem.Dto.Other.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoUser{
    public UUID id;
    public String username;
    public String email;
    public String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    public LocalDateTime created_at;
    public LocalDateTime updated_at;
}
