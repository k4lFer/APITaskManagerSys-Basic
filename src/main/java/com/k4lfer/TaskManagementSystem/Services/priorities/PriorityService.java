package com.k4lfer.TaskManagementSystem.Services.priorities;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

import com.k4lfer.TaskManagementSystem.Dto.Objects.DtoPriority;
import com.k4lfer.TaskManagementSystem.models.Priority;
import com.k4lfer.TaskManagementSystem.repositories.PriorityRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PriorityService {
    private final PriorityRepository priorityRepository;

    public List<DtoPriority> getAll(){
        List<Priority> priorities = priorityRepository.findAll();
        List<DtoPriority> dtoPriorities = new ArrayList<>();

        for(Priority priority : priorities){
            DtoPriority dtoPriority = new DtoPriority();
            dtoPriority.setId(priority.getId());
            dtoPriority.setName(priority.getName());
            dtoPriority.setLevel(priority.getLevel());
            dtoPriority.setCreated_at(priority.getCreated_at());
            dtoPriority.setUpdated_at(priority.getUpdated_at());
            dtoPriorities.add(dtoPriority);
        }
        return dtoPriorities;
    }
}
