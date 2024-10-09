package com.k4lfer.TaskManagementSystem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.k4lfer.TaskManagementSystem.Services.UserService;
import com.k4lfer.TaskManagementSystem.models.User;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<DtoResponse> createUser(@RequestBody DtoUser dtoUser) {
        DtoResponse response = userService.registerUser(dtoUser);
        HttpStatus status = HttpStatusMapper.map(response.getStatusCode());
        return new ResponseEntity<>(response, status);
    }
    
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody DtoAuth authRequest) {

        User user = userService.loadUserByUsernameOrEmail(authRequest.getUsernameOrEmail());
        
        if (user == null || !passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
        }

        String accessToken = jwtTokenService.generateAccessToken(user.getId(), user.getRole().toString());
        String refreshToken = jwtTokenService.generateRefreshToken(user.getId(), user.getRole().toString());
        
        TokenResponse token = new TokenResponse(accessToken, refreshToken);
        return ResponseEntity.ok(token); 
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody TokenResponse tokenResponse) {
        try {
            // Generar un nuevo access token usando el refresh token
            String newAccessToken = jwtTokenService.refreshAccessToken(tokenResponse.refreshToken);
            
            // Retorna el nuevo access token en la respuesta
            tokenResponse = new TokenResponse(newAccessToken, tokenResponse.refreshToken); // Retorna el mismo refresh token
            return ResponseEntity.ok(tokenResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Retorna 401 si el refresh token es inválido
        }
    }

}
