package com.k4lfer.TaskManagementSystem.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k4lfer.TaskManagementSystem.Dto.Objects.DtoPriority;
import com.k4lfer.TaskManagementSystem.Services.priorities.PriorityService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/priority")
@AllArgsConstructor
public class PriorityController {
    private final PriorityService priorityService;

    @GetMapping("/")
    public ResponseEntity<List<DtoPriority>> getAll() {
        return ResponseEntity.ok(priorityService.getAll());
    }
    
}
