package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.utils.JWT;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWT jwt;

    @RequestMapping(value = "api/users/auth", method = RequestMethod.POST)
    public String auth(@RequestBody UserModel user) {

        UserModel userLogged = userRepository.authenticateUser(user);
        if (userLogged != null) {
            String tokenJwt = jwt.create(String.valueOf(userLogged.getId()), userLogged.getUsername());
            return tokenJwt;
        }
        return "FAIL";
    }
}