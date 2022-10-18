package com.urgent2k.employeeDRH.Security;

public class JwtProperties {
    public static final String SECRET = "Urgent2kSecret";
    public static final int EXPIRATION_TIME = 180_000; // (3mins)in millisecs want to experiment on rapidly expiring tokens
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
