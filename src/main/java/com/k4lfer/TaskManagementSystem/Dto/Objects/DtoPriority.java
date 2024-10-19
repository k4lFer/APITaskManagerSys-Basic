package com.k4lfer.TaskManagementSystem.Dto.Objects;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoPriority {
    private UUID id;
    private String name;
    private Integer level;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
