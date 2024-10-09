package com.k4lfer.TaskManagementSystem.Dto.Other;

import org.springframework.http.HttpStatus;

public class HttpStatusMapper {
    public static HttpStatus map(int statusCode){
        return switch(statusCode){
            case 200 -> HttpStatus.OK;
            case 201 -> HttpStatus.CREATED;
            case 400 -> HttpStatus.BAD_REQUEST;
            case 500 -> HttpStatus.INTERNAL_SERVER_ERROR;
            default  -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
