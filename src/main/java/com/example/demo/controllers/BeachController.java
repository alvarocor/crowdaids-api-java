package com.example.demo.controllers;

import com.example.demo.repositories.BeachImplementations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class BeachController {

    @Autowired
    private BeachImplementations beachImplementations;

    @RequestMapping(value = "/api/forecast/swell", method = RequestMethod.GET)
    public List retrieveSwellConditions(@RequestParam String spotId) {

        List swell = beachImplementations.retrieveSwellConditions(spotId);

        return swell;
    }
}
