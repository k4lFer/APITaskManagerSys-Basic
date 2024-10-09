package com.k4lfer.TaskManagementSystem.Dto.Other;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoResponse {
    private List<String> listMessage = new ArrayList<>();
    private String type;
    private int statusCode; // Nuevo campo para códigos de estado

    public boolean existsMessage() {
        return !listMessage.isEmpty();
    }

    public void addMessage(String message) {
        listMessage.add(message);
    }

    public void success(String message) {
        type = "success";
        statusCode = 200; // Código de éxito por defecto
        addMessage(message);
    }

    public void created(String message) {
        type = "success";
        statusCode = 201; // Código para recursos creados
        addMessage(message);
    }

    public void error(String message) {
        type = "error";
        statusCode = 400; // Código de error por defecto
        addMessage(message);
    }

    public void warning(String message) {
        type = "warning";
        statusCode = 300; // Código de advertencia, puede ser ajustado
        addMessage(message);
    }

    public void exception(String message) {
        type = "exception";
        statusCode = 500; // Error interno del servidor
        addMessage(message);
    }

    // Getter para la lista de mensajes
    public List<String> getListMessage() {
        return listMessage;
    }
}
