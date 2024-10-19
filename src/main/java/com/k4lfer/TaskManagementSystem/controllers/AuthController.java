package com.k4lfer.TaskManagementSystem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k4lfer.TaskManagementSystem.Dto.Objects.DtoAuth;
import com.k4lfer.TaskManagementSystem.Dto.Objects.DtoUser;
import com.k4lfer.TaskManagementSystem.Dto.Other.DtoResponse;
import com.k4lfer.TaskManagementSystem.Dto.Other.HttpStatusMapper;
import com.k4lfer.TaskManagementSystem.Dto.Other.TokenResponse;
import com.k4lfer.TaskManagementSystem.Services.Jwt.JwtTokenService;
import com.k4lfer.TaskManagementSystem.Services.user.UserResponse;
import com.k4lfer.TaskManagementSystem.Services.user.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    
    private final UserService userService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/register")
    public ResponseEntity<DtoResponse> createUser(@RequestBody DtoUser dtoUser) {
        DtoResponse response = userService.registerUser(dtoUser);
        HttpStatus status = HttpStatusMapper.map(response.getStatusCode());
        return new ResponseEntity<>(response, status);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody DtoAuth authRequest) {
        UserResponse userResponse = userService.login(authRequest);

        HttpStatus status = HttpStatusMapper.map(userResponse.getDtoResponse().getStatusCode());
        if (userResponse.getDtoResponse().getStatusCode() != 200) {
            return new ResponseEntity<>(userResponse.getDtoResponse(), status); 
        }

        String accessToken = jwtTokenService.generateAccessToken(userResponse.getDtoUser().getId(), userResponse.getDtoUser().getRole().toString());
        String refreshToken = jwtTokenService.generateRefreshToken(userResponse.getDtoUser().getId(), userResponse.getDtoUser().getRole().toString());
        
        //TokenResponse tokenResponse = new TokenResponse(accessToken, refreshToken);
        userResponse.setTokenResponse(new TokenResponse(accessToken, refreshToken));
        return new ResponseEntity<>(userResponse, status);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody TokenResponse tokenResponse) {
        try {
            String newAccessToken = jwtTokenService.refreshAccessToken(tokenResponse.refreshToken);
            
            tokenResponse = new TokenResponse(newAccessToken, tokenResponse.refreshToken); // Retorna el mismo refresh token
            return ResponseEntity.ok(tokenResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Retorna 401 si el refresh token es inválido
        }
    }

}
