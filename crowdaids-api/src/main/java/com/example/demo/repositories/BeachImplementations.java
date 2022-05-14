package com.example.demo.repositories;

import com.example.demo.models.BeachInformationModel;

import com.example.demo.utils.ConverterInfoBeach;

import org.hibernate.exception.DataException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Repository
@Transactional
public class BeachImplementations implements BeachRepository {
	
    @Override
    public BeachInformationModel retrieveSwellConditions(String id) {
        String url = "http://services.surfline.com/kbyg/spots/forecasts/wave?spotId=" + id + "&days=6&intervalHours=1";
        RestTemplate restTemplate = new RestTemplate();
        LinkedHashMap result = ((LinkedHashMap) restTemplate.getForObject(url, Object.class));
        //String result = restTemplate.getForObject(url, String.class);)

        BeachInformationModel swellConditions = new BeachInformationModel();
        Object swells = result.get("data");
        
        // SET TIMESTAMP
        String utc = result.get("associated").toString().substring(92, 95);
        int utcOffset = 0;
        
        if (utc.contains("-")) {
        	utc = result.get("associated").toString().substring(93, 94);
        	utcOffset = Integer.parseInt(utc);
        	utcOffset = -utcOffset;
        } else {
        	utcOffset = Integer.parseInt(utc.substring(0, 1));
        }
        
        long utcFinal = ConverterInfoBeach.getUtcBeach(utcOffset);
        swellConditions.setTimestamp(utcFinal);;

        // SET LATITUDE, LONGITUDE & COORDMAP
        int lonIndex = result.get("associated").toString().indexOf("lon=");
        int latIndex = result.get("associated").toString().indexOf("lat=");
        int forecastLocationIndex = result.get("associated").toString().indexOf("forecastLocation");
        
        String lonString = result.get("associated").toString().substring(lonIndex + 4 , latIndex - 2);
        float lon = Float.parseFloat(lonString);
        swellConditions.setLocationLon(lon);
        
        String latString = result.get("associated").toString().substring(latIndex + 4, forecastLocationIndex - 3);
        float lat = Float.parseFloat(latString);
        swellConditions.setLocationLat(lat);
        
        String coordMap = ConverterInfoBeach.toXY(swellConditions.getLocationLon(), swellConditions.getLocationLat());
        swellConditions.setCoordMap(coordMap);
        
        // SET SWELL HEIGTH
        List waveInfoString = (List) ((LinkedHashMap) swells).get("wave");
        List swellHeigth = ConverterInfoBeach.getHeigthMaxMin(waveInfoString, swellConditions.getTimestamp());
        
        swellConditions.setSwellHeigthMax(Double.parseDouble((String) swellHeigth.get(0)));
        swellConditions.setSwellHeigthMin(Double.parseDouble((String) swellHeigth.get(1)));
        
        // SET ARROW SWELL
        List arrowSwell = ConverterInfoBeach.getDirectionSwell(waveInfoString, swellConditions.getTimestamp());
        swellConditions.setDirectionSwell(Double.parseDouble((String) arrowSwell.get(0)));
        swellConditions.setDirectionSwellMin(Double.parseDouble((String) arrowSwell.get(1)));
        
        //SET OBJECT INFO WAVE
        Object infoSwell = ((LinkedHashMap) swells).get("wave");
        swellConditions.setInfoWave(infoSwell);
        
        return swellConditions;
    }
    
    /**
     * 
     * @param id
     * @return
     */
    @Override
    public BeachInformationModel retrieveWeatherConditions(String id) {
    	String url = "http://services.surfline.com/kbyg/spots/forecasts/weather?spotId=" + id + "&days=6&intervalHours=1";
        RestTemplate restTemplate = new RestTemplate();
        LinkedHashMap result = ((LinkedHashMap) restTemplate.getForObject(url, Object.class));
        
        BeachInformationModel weatherConditions = new BeachInformationModel();
        Object info = result.get("data");
        
        // SET TIMESTAMP
        String utc = result.get("associated").toString().substring(92, 95);
        int utcOffset = 0;
        
        if (utc.contains("-")) {
        	utc = result.get("associated").toString().substring(93, 94);
        	utcOffset = Integer.parseInt(utc);
        	utcOffset = -utcOffset;
        } else {
        	utcOffset = Integer.parseInt(utc.substring(0, 1));
        }
        
        long utcFinal = ConverterInfoBeach.getUtcBeach(utcOffset);
        weatherConditions.setTimestamp(utcFinal);
        
        //SET WEATHER INFO
        List infoWeather = (List) ((LinkedHashMap) info).get("weather");
        List weather = ConverterInfoBeach.getWeatherInfo(infoWeather, weatherConditions.getTimestamp());
        weatherConditions.setIconTemperature((String) weather.get(1));
        weatherConditions.setTemperature(Double.parseDouble((String) weather.get(0)));
        
        //SET OBJECT WEATHER INFO
        Object objectWeather = ((LinkedHashMap) info).get("weather");
        weatherConditions.setInfoWeather(objectWeather);
       
		return weatherConditions;
    }
    
    /**
     * 
     */
    @Override
    public BeachInformationModel retrieveTidesConditions(String id) {
    	String url = "http://services.surfline.com/kbyg/spots/forecasts/tides?spotId=" + id + "&days=6";
        RestTemplate restTemplate = new RestTemplate();
        LinkedHashMap result = ((LinkedHashMap) restTemplate.getForObject(url, Object.class));
        
        BeachInformationModel tideConditions = new BeachInformationModel();
        Object info = result.get("data");
        
        // SET TIMESTAMP
        int indexUTC = result.get("associated").toString().indexOf("utcOffset=");
        String utc = result.get("associated").toString().substring(indexUTC + 10, indexUTC + 12);
        int utcOffset = 0;
        
        if (utc.contains("-")) {
        	utc = result.get("associated").toString().substring(indexUTC + 11, indexUTC + 12);
        	utcOffset = Integer.parseInt(utc);
        	utcOffset = -utcOffset;
        } else {
        	utcOffset = Integer.parseInt(utc.substring(0, 1));
        }
        
        long utcFinal = ConverterInfoBeach.getUtcBeach(utcOffset);
        tideConditions.setTimestamp(utcFinal);
        
        //SET TIDE INFO
        List infoTide = (List) ((LinkedHashMap) info).get("tides");
        String tide = ConverterInfoBeach.getTideInfo(infoTide, tideConditions.getTimestamp());
        tideConditions.setTideHeight(Double.parseDouble(tide));
        
        //SET OBJECT TIDE INFO
        Object objectTide = ((LinkedHashMap) info).get("tides");
        tideConditions.setInfoTide(objectTide);
        
		return tideConditions;
    }
    
    /**
     * 
     */
    @Override
    public BeachInformationModel retrieveWindConditions(String id) {
    	String url = "http://services.surfline.com/kbyg/spots/forecasts/wind?spotId=" + id + "&days=6&intervalHours=1";
        RestTemplate restTemplate = new RestTemplate();
        LinkedHashMap result = ((LinkedHashMap) restTemplate.getForObject(url, Object.class));
        
        BeachInformationModel windConditions = new BeachInformationModel();
        Object info = result.get("data");
        
        // SET TIMESTAMP
        int indexUTC = result.get("associated").toString().indexOf("utcOffset=");
        String utc = result.get("associated").toString().substring(indexUTC + 10, indexUTC + 12);
        int utcOffset = 0;
        
        if (utc.contains("-")) {
        	utc = result.get("associated").toString().substring(indexUTC + 11, indexUTC + 12);
        	utcOffset = Integer.parseInt(utc);
        	utcOffset = -utcOffset;
        } else {
        	utcOffset = Integer.parseInt(utc.substring(0, 1));
        }
        
        long utcFinal = ConverterInfoBeach.getUtcBeach(utcOffset);
        windConditions.setTimestamp(utcFinal);
        
        //SET WIND INFO
        List infoWind = (List) ((LinkedHashMap) info).get("wind");
        List wind = ConverterInfoBeach.getWindInfo(infoWind, windConditions.getTimestamp());
        windConditions.setSpeedWind(Double.parseDouble((String) wind.get(0)));
        windConditions.setDirectionWind(Double.parseDouble((String) wind.get(2)));
        windConditions.setDirectionTypeWind((String) wind.get(1));
        
        //SET OBJECT WIND INFO
        Object objectWind = ((LinkedHashMap) info).get("wind");
        windConditions.setInfoWind(objectWind);
    	
		return windConditions;
    }
}
