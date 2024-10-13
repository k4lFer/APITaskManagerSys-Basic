package com.k4lfer.TaskManagementSystem.Dto.Other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    public String accessToken;
    public String refreshToken;
}
