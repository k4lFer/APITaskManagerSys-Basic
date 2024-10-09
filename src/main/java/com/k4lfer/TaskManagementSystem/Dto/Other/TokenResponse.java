package com.k4lfer.TaskManagementSystem.Dto.Other;

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
public class TokenResponse {
    public String accessToken;
    public String refreshToken;
}
