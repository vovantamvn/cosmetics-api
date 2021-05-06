package com.app.cosmetics.security;

import com.auth0.jwt.algorithms.Algorithm;

public class JWTConstant {

    private JWTConstant(){}

    private static final String SECRET = "secret";

    public static final int EXPIRATION_TIME = 15 * 60 * 1000;
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final Algorithm ALGORITHM = Algorithm.HMAC512(SECRET.getBytes());
}
