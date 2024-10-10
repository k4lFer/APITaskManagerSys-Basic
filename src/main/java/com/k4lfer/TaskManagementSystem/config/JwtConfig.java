package com.k4lfer.TaskManagementSystem.config;

public class JwtConfig {
    public static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static long accessTokenExpirationMs = 24 * 60 * 60 * 1000; // 1 día en milisegundos
    public static long refreshTokenExpirationMs = 7 * 24 * 60 * 60 * 1000; // 7 días en milisegundos
}
