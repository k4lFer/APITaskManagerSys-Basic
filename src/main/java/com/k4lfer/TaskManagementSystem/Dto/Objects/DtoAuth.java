package com.k4lfer.TaskManagementSystem.Dto.Objects;

import lombok.Data;

@Data
public class DtoAuth {
    private String usernameOrEmail; 
    private String password;
}
