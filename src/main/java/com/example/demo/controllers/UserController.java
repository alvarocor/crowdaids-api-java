package com.example.demo.controllers;

import com.example.demo.repositories.UserImplementations;
import com.example.demo.utils.JWT;
import com.example.demo.models.UserModel;

import com.example.demo.utils.validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController

public class UserController {

    @Autowired
    private UserImplementations userImplementations;

    @Autowired
    private JWT jwt;

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public UserModel getUser(@RequestHeader(value="Authorization") String token) {
        validators.validateToken(token);

        String bearerToken = token;
        String[] parts = bearerToken.split(" ");
        String finalToken = parts[1];

        String userId = validateToken(finalToken);

        if (userId == null) { return null; }

        UserModel user = userImplementations.getUser(userId);

        return user;
    }

    private String validateToken(String token) {
        String userId = jwt.getKey(token);
        return userId;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public void registerUser(@RequestBody UserModel user) {
        validators.validateUsername(user.getUsername());
        validators.validateFullname(user.getFullname());
        validators.validatePassword(user.getPassword());
        validators.validateEmail(user.getEmail());

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, user.getPassword());
        user.setPassword(hash);

        userImplementations.register(user);
    }

    @RequestMapping(value = "/api/users/update", method = RequestMethod.PATCH)
    public void modifyUser(@RequestHeader(value="Authorization") String token,
                           @RequestBody UserModel newData) {
        validators.validateToken(token);
        validators.validationData(newData);

        String bearerToken = token;
        String[] parts = bearerToken.split(" ");
        String finalToken = parts[1];

        String userId = validateToken(finalToken);

        UserModel user1 = userImplementations.getUser(userId);

        userImplementations.modifyUser(user1, newData);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.DELETE)
    public void delete(@RequestHeader(value="Authorization") String token,
                       @RequestBody UserModel password) {
        validators.validateToken(token);

        String bearerToken = token;
        String[] parts = bearerToken.split(" ");
        String finalToken = parts[1];

        String userId = validateToken(finalToken);

        userImplementations.delete(userId, password);
    }
}