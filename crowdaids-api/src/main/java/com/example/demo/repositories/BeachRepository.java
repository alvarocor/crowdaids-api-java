package com.example.demo.repositories;

import org.springframework.stereotype.Repository;

import com.example.demo.models.BeachInformationModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface BeachRepository {

	BeachInformationModel retrieveSwellConditions(String id);
	
	BeachInformationModel retrieveWeatherConditions(String id);
	
	BeachInformationModel retrieveTidesConditions(String id);
	
	BeachInformationModel retrieveWindConditions(String id);
}
