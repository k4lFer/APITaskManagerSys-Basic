package com.k4lfer.TaskManagementSystem.Services.user;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.k4lfer.TaskManagementSystem.Dto.Objects.DtoAuth;
import com.k4lfer.TaskManagementSystem.Dto.Objects.DtoUser;
import com.k4lfer.TaskManagementSystem.Dto.Other.DtoResponse;
import com.k4lfer.TaskManagementSystem.Dto.Other.Role;
import com.k4lfer.TaskManagementSystem.models.User;
import com.k4lfer.TaskManagementSystem.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Optional<User> loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> loadUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserResponse login(DtoAuth dtoAuth){
        UserResponse userResponse = new UserResponse();

        User user = loadUserByUsernameOrEmail(dtoAuth.getUsernameOrEmail());
        if (user == null || !passwordEncoder.matches(dtoAuth.getPassword(), user.getPassword())) {
            userResponse.dtoResponse.error("El usuario o la contraseña no coincide, revise sus credenciales");
            return userResponse; 
        }
        userResponse.dtoResponse.success("Bienvenido!!");
        userResponse.setDtoUser(new DtoUser(user.getId(), user.getUsername(), user.getEmail(), null, user.getRole(), user.getCreated_at(), user.getUpdated_at()));
        return userResponse;
    }
    
    public DtoUser findUserById(UUID id) {
        return userRepository.findById(id)
         .map(user -> {
            DtoUser dtoUser = new DtoUser();
            dtoUser.setId(user.getId());
            dtoUser.setUsername(user.getUsername());
            dtoUser.setEmail(user.getEmail());
            dtoUser.setRole(user.getRole());
            dtoUser.setCreated_at(user.getCreated_at());
            dtoUser.setUpdated_at(user.getUpdated_at());
            return dtoUser;
        }).orElse(null);
    }

    public DtoResponse registerUser(DtoUser dtoUser) {
        DtoResponse dtoResponse = new DtoResponse();
        Optional<User> userByUsername = userRepository.findByUsername(dtoUser.getUsername());
        Optional<User> userByEmail = userRepository.findByEmail(dtoUser.getEmail());

        if (userByUsername.isPresent()) {
            dtoResponse.error("El nombre de usuario ya existe.");
            return dtoResponse;
        }

        if (userByEmail.isPresent()) {
            dtoResponse.error("El correo electrónico ya existe.");
            return dtoResponse;
        }        
    
        try {
            User newUser = new User();
            newUser.setId(UUID.randomUUID());
            newUser.setUsername(dtoUser.getUsername());
            newUser.setPassword(passwordEncoder.encode(dtoUser.getPassword()));
            newUser.setRole(Role.user); 
            newUser.setEmail(dtoUser.getEmail());
            newUser.setCreated_at(LocalDateTime.now());
            newUser.setUpdated_at(LocalDateTime.now());
            
            userRepository.save(newUser);
            dtoResponse.created("Registro exitoso.");
            return dtoResponse;
    
        } catch (Exception e) {
            dtoResponse.exception("Error al registrarse: " + e.getMessage());
            return dtoResponse;
        }
    }
    
    public User loadUserByUsernameOrEmail(String usernameOrEmail) {
        Optional<User> userByUsername = loadUserByUsername(usernameOrEmail);
        if (userByUsername.isPresent()) {
            return userByUsername.get();
        }

        Optional<User> userByEmail = loadUserByEmail(usernameOrEmail);
        return userByEmail.orElse(null); 
    }
}
