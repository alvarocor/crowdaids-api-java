package com.example.demo.controllers;
import com.example.demo.models.UserModel;
import com.example.demo.models.TokenModel;
import com.example.demo.repositories.UserImplementations;
import com.example.demo.utils.JWT;

import com.example.demo.utils.validators;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class AuthController {

    @Autowired
    private UserImplementations userImplementations;

    @Autowired
    private JWT jwt;

    @RequestMapping(value = "api/users/auth", method = RequestMethod.POST)
    public TokenModel auth(@RequestBody UserModel user) {
        validators.validateUsername(user.getUsername());
        validators.validatePassword(user.getPassword());

        UserModel userLogged = userImplementations.authenticateUser(user);
        if (userLogged != null) {

            String tokenJwt = jwt.create(String.valueOf(userLogged.getId()), userLogged.getUsername());
            TokenModel tokenJSON = new TokenModel(tokenJwt);
            tokenJSON.setToken(tokenJwt);
            return tokenJSON;
        }
        return null;
    }
}