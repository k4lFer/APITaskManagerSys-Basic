package com.k4lfer.TaskManagementSystem.Services.tasks;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.k4lfer.TaskManagementSystem.Dto.Objects.DtoTask;
import com.k4lfer.TaskManagementSystem.Dto.Objects.PageResponse;
import com.k4lfer.TaskManagementSystem.Dto.Other.DtoResponse;
import com.k4lfer.TaskManagementSystem.models.Category;
import com.k4lfer.TaskManagementSystem.models.Priority;
import com.k4lfer.TaskManagementSystem.models.Tasks;
import com.k4lfer.TaskManagementSystem.models.User;
import com.k4lfer.TaskManagementSystem.repositories.CategoryRepository;
import com.k4lfer.TaskManagementSystem.repositories.PriorityRepository;
import com.k4lfer.TaskManagementSystem.repositories.TaskRepository;
import com.k4lfer.TaskManagementSystem.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final PriorityRepository priorityRepository;
    private final UserRepository userRepository;

    public DtoResponse createTask(DtoTask dtoTask){
        DtoResponse dtoResponse = new DtoResponse();
        Optional<User> user = userRepository.findById(dtoTask.getUserId());
        Optional<Category> category = categoryRepository.findById(dtoTask.getCategoryId());
        Optional<Priority> priority = priorityRepository.findById(dtoTask.getPriorityId());
        
        try {
            Tasks newTask = new Tasks();
            newTask.setId(UUID.randomUUID());
            newTask.setTitle(dtoTask.getTitle());
            newTask.setDescription(dtoTask.getDescription());
            newTask.setStatus(dtoTask.getStatus());
            newTask.setDueDate(dtoTask.getDueDate()); // Fecha limite
            newTask.setUser(user.get());  // Establecer el objeto User completo
            newTask.setCategory(category.get());  // Establecer el objeto Category completo
            newTask.setPriority(priority.get());  // Establecer el objeto Priority completo
            newTask.setCreatedAt(LocalDateTime.now());
            newTask.setUpdatedAt(LocalDateTime.now());
    
            taskRepository.save(newTask);
            dtoResponse.created("Tarea creada exitosamente");
            return dtoResponse;
        } catch (Exception e) {
            dtoResponse.exception("Error al crear la tarea: " + e.getMessage());
            return dtoResponse;
        }
    }

    public PageResponse<DtoTask> listTasks(UUID userId, UUID categoryId, UUID priorityId, int page, int size) {
        PageResponse<DtoTask> pageResponse = new PageResponse<>();

        Pageable pageable = PageRequest.of(page, size);
        Page<Tasks> tasksPage;

        if (categoryId != null && priorityId != null) {
            tasksPage = taskRepository.findByUserIdAndCategoryIdAndPriorityId(userId, categoryId, priorityId, pageable);
        } else if (categoryId != null) {
            tasksPage = taskRepository.findByUserIdAndCategoryId(userId, categoryId, pageable);
        } else if (priorityId != null) {
            tasksPage = taskRepository.findByUserIdAndPriorityId(userId, priorityId, pageable);
        } else {
            tasksPage = taskRepository.findByUserId(userId, pageable);
        }

        List<DtoTask> tasksDtoList = tasksPage.getContent().stream().map(task -> {
            DtoTask dtoTask = new DtoTask();
            dtoTask.setId(task.getId());
            dtoTask.setTitle(task.getTitle());
            dtoTask.setDescription(task.getDescription());
            dtoTask.setStatus(task.getStatus());
            dtoTask.setDueDate(task.getDueDate());
            dtoTask.setUserId(task.getUser().getId());
            dtoTask.setCategoryId(task.getCategory().getId());
            dtoTask.setCategoryName(task.getCategory().getName());
            dtoTask.setPriorityId(task.getPriority().getId());
            dtoTask.setPriorityName(task.getPriority().getName());
            dtoTask.setCreatedAt(task.getCreatedAt());
            dtoTask.setUpdatedAt(task.getUpdatedAt());
            return dtoTask;
        }).collect(Collectors.toList());

        // Establecer la respuesta paginada
        pageResponse.setContent(tasksDtoList);
        pageResponse.setTotalPages(tasksPage.getTotalPages());
        pageResponse.setTotalElements(tasksPage.getTotalElements());
        pageResponse.setPageSize(tasksPage.getSize());
        pageResponse.setNumber(tasksPage.getNumber());

        return pageResponse;
    }

}
