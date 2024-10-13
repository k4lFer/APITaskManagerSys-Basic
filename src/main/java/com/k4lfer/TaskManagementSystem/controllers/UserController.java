package com.k4lfer.TaskManagementSystem.controllers;

import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k4lfer.TaskManagementSystem.Dto.Objects.DtoUser;
import com.k4lfer.TaskManagementSystem.Services.Jwt.JwtTokenService;
import com.k4lfer.TaskManagementSystem.Services.user.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenService tokenService;
    
    @GetMapping("/profile")
    public ResponseEntity<DtoUser> profile(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        UUID id = tokenService.getUserIdFromToken(token);
        DtoUser userDto = userService.findUserById(id);
        return ResponseEntity.ok(userDto); 
    }

}
