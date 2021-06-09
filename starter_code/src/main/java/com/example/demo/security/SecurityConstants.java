package com.example.demo.security;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 1728_000_000; // 20 days
    public static final String SECRET = "SecurityKeys";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer " ;
    public static final String Sign_UP_URL = "/api/user/create";
}
