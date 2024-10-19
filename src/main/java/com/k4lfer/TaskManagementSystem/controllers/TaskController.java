package com.k4lfer.TaskManagementSystem.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.k4lfer.TaskManagementSystem.Dto.Objects.DtoTask;
import com.k4lfer.TaskManagementSystem.Dto.Objects.PageResponse;
import com.k4lfer.TaskManagementSystem.Dto.Other.DtoResponse;
import com.k4lfer.TaskManagementSystem.Dto.Other.HttpStatusMapper;
import com.k4lfer.TaskManagementSystem.Services.Jwt.JwtTokenService;
import com.k4lfer.TaskManagementSystem.Services.tasks.TaskService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final JwtTokenService tokenService;
    private final TaskService taskService;

    @PostMapping("/new-task")
    public ResponseEntity<DtoResponse> newTask(@RequestHeader("Authorization") String authorizationHeader,@RequestBody DtoTask dtoTask) { 
        String token = authorizationHeader.substring(7);
        UUID userId = tokenService.getUserIdFromToken(token);
        dtoTask.setUserId(userId);
        DtoResponse dtoResponse = taskService.createTask(dtoTask);
        HttpStatus status = HttpStatusMapper.map(dtoResponse.getStatusCode());
        return new ResponseEntity<>(dtoResponse, status);
    }

    @GetMapping("/list-tasks")
    public ResponseEntity<PageResponse<DtoTask>> listTasks(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "categoryId", required = false) UUID categoryId,
            @RequestParam(value = "priorityId", required = false) UUID priorityId) {
        
        String token = authorizationHeader.substring(7);
        UUID userId = tokenService.getUserIdFromToken(token);
        
        PageResponse<DtoTask> tasksPageResponse = taskService.listTasks(userId, categoryId, priorityId, page, size);
        return new ResponseEntity<>(tasksPageResponse, HttpStatus.OK);
    }

}
