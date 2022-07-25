package com.subscribed.clock.utils;

import io.jsonwebtoken.*;

import java.util.Calendar;
import java.util.Date;

import io.jsonwebtoken.Jwts;

public class Jwt {
    // todo This is here only for simplicity, it should be set through environmental vars
    private static String JWT_SECRET = "secret-subscribed-clock-app";

    public static String createJWT(String id, String issuer, String subject) {
        return Jwts.builder().setId(id)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }
}
