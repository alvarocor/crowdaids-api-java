package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.utils.JWT;

public class SanitizeToken {

	@Autowired
    private static  JWT jwt;

    public static  String getId(String token) {

        String bearerToken = token;
        String[] parts = bearerToken.split(" ");
        String finalToken = parts[1];

        String userId = validateToken(finalToken);

        return userId;
    }

    private static  String validateToken(String token) {
        String userId =  jwt.getKey(token);
        return userId;
    }
}
