package com.example.demo.controllers;

import com.example.demo.models.BeachInformationModel;
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
    public BeachInformationModel retrieveSwellConditions(@RequestParam String spotId) {

    	BeachInformationModel swell = beachImplementations.retrieveSwellConditions(spotId);

        return swell;
    }
    
    @RequestMapping(value = "/api/forecast/weather", method = RequestMethod.GET)
    public BeachInformationModel retrieveWeatherConditions(@RequestParam String spotId) {
    	
    	BeachInformationModel weather = beachImplementations.retrieveWeatherConditions(spotId);
    	
    	return weather;
    }
    
    @RequestMapping(value = "/api/forecast/tides", method = RequestMethod.GET)
    public BeachInformationModel retrieveTidesConditions(@RequestParam String spotId) {
    	
    	BeachInformationModel tides = beachImplementations.retrieveTidesConditions(spotId);
    	
		return tides;
    }
    
    @RequestMapping(value = "/api/forecast/wind", method = RequestMethod.GET)
    public BeachInformationModel retrieveWindConditions(@RequestParam String spotId) {
    	
    	BeachInformationModel wind = beachImplementations.retrieveWindConditions(spotId);
    	
    	return wind;
    }
}
