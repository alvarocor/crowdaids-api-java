package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserImplementations;
import com.example.demo.utils.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController

public class UserController {

    @Autowired
    private UserImplementations userImplementations;

    @Autowired
    private JWT jwt;

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public List<UserModel> getUsers(@RequestHeader(value="Authorization") String token) {
        if (!validateToken(token)) { return null; }

        return userImplementations.getUsers();
    }

    private boolean validateToken(String token) {
        String userId = jwt.getKey(token);
        return userId != null;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public void registerUser(@RequestBody UserModel user) {

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, user.getPassword());
        user.setPassword(hash);

        userImplementations.register(user);
    }

    //@RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE)
    //public void delete(@RequestHeader(value="Authorization") String token, @PathVariable Long id) {
    //    if (!validateToken(token)) { return; }
    //   userImplementations.delete(id);
    //}
}