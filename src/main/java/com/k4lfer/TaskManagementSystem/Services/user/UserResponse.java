package com.k4lfer.TaskManagementSystem.Services.user;

import com.k4lfer.TaskManagementSystem.Dto.Objects.DtoUser;
import com.k4lfer.TaskManagementSystem.Dto.Other.DtoResponse;
import com.k4lfer.TaskManagementSystem.Dto.Other.TokenResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    public DtoResponse dtoResponse = new DtoResponse();
    public DtoUser dtoUser = new DtoUser();
    public TokenResponse tokenResponse = new TokenResponse(); 
}
