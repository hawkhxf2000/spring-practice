package com.example.springjwt.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

public class JWTUtil {
    static final int TIME = 1000 * 60 * 60 * 24;
    static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    static String signature = "admin";

    public static String createToken(){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //Header
                .setHeaderParam("type", "JWT")
                .setHeaderParam("alg","HS256")
                //payload
                .claim("username","zs")
                .claim("role","admin")
                .setSubject("admin-test")
                .setExpiration(new Date(System.currentTimeMillis() + TIME))
                .setId(UUID.randomUUID().toString())
                .signWith(key)
                .compact();
        return jwtToken;
    }
}
