package com.example.demo.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConverterInfoBeach {
	
	/**
	 * 
	 * @param info
	 * @param time
	 * @return
	 */
	public static List getHeigthMaxMin(List info, long time) {
		List response = new ArrayList();
		String swellMax = "";
		String swellMin = "";
		String timeToCheck = Long.toString(time);
		int indexSwellMax = info.get(0).toString().indexOf("max=");
		int indexSwellMin = info.get(0).toString().indexOf("min=");
		
		for (int i = 0; i < info.size(); i++) {
			if (info.get(i).toString().substring(11, 21) == timeToCheck) {
				swellMax = info.get(i).toString().substring(indexSwellMax + 4, indexSwellMax + 7);
				swellMin = info.get(i).toString().substring(indexSwellMin + 4, indexSwellMin +7);
				
				if(swellMax.contains(",")) {
					swellMax = swellMax.substring(0, swellMax.indexOf(","));
				} 
				
				if(swellMin.contains(",")) {
					swellMin = swellMin.substring(0, swellMin.indexOf(","));
				} 
				
				response.add(swellMax);
				response.add(swellMin);
				return response;
			}	
		}
		swellMax = info.get(0).toString().substring(indexSwellMax + 4, indexSwellMax + 7);
		swellMin = info.get(0).toString().substring(indexSwellMin + 4, indexSwellMin +7);
		
		if(swellMax.contains(",")) {
			swellMax = swellMax.substring(0, swellMax.indexOf(","));
		} 	
		if(swellMin.contains(",")) {
			swellMin = swellMin.substring(0, swellMin.indexOf(","));
		} 
			
		response.add(swellMax);
		response.add(swellMin);
		return response;
	}
	
	/**
	 * 
	 * @param utc
	 * @return
	 */
	public static long getUtcBeach(int utc) {
		
		OffsetDateTime newDate = OffsetDateTime.now();
		String date = newDate.toString();
		
		int utcLength = date.length();
		utcLength -= 5;
		
		int utcUser = Integer.parseInt(date.substring(utcLength, utcLength + 2));
		int year = newDate.getYear();
		int month = newDate.getMonthValue();
		int day = newDate.getDayOfMonth();
		int hour = newDate.getHour();
		
		int utcApiBeach = utc - (utcUser);
		int realUtcBeach = utcApiBeach - (utcUser);
		long newDateUTC = 0;
		 
		 if (realUtcBeach < 0) {
			 hour += realUtcBeach;
			 newDateUTC = java.util.Date.UTC(year, month, day, hour, 0, 0);
		 } else if (realUtcBeach >= 0) {
			 hour -= realUtcBeach;
			 newDateUTC = java.util.Date.UTC(year, month, day, hour, 0, 0);
		 }
		 
		long timestamp = newDateUTC / 1000;
		
		return timestamp;
	}
	
	/**
	 * 
	 * @param info
	 * @param time
	 * @return
	 */
	public static List getDirectionSwell(List info, long time) {
		 List response = new ArrayList();
		String directionSwell = "";
		String directionSwellMin = "";
		String timeToCheck = Long.toString(time);
		int indexDirection = info.get(0).toString().indexOf("direction=");
		int indexDirectionMin = info.get(0).toString().indexOf("directionMin=");
		
		for (int i = 0; i < info.size(); i++) {
			if (info.get(i).toString().substring(11, 21) == timeToCheck) {
				if (info.get(i).toString().substring(indexDirection + 10, indexDirection + 12).equals("0,")) {
					directionSwell = info.get(i).toString().substring(indexDirection + 10, indexDirection + 11);
					response.add(directionSwell);
				} else {
					directionSwell = info.get(i).toString().substring(indexDirection + 10, indexDirection + 17);
					response.add(directionSwell);
				}
				
				if (info.get(i).toString().substring(indexDirectionMin + 13, indexDirectionMin + 15).equals("0,")) {
					directionSwellMin = info.get(i).toString().substring(indexDirectionMin + 13, indexDirectionMin + 14);
					response.add(directionSwellMin);
				} else {
					directionSwellMin = info.get(i).toString().substring(indexDirectionMin + 13, indexDirectionMin + 20);
					response.add(directionSwellMin);
				}
				
				return response;
			}
		}
		
		if (info.get(0).toString().substring(indexDirection + 10, indexDirection + 12).equals("0,")) {
			directionSwell = info.get(0).toString().substring(indexDirection + 10, indexDirection + 11);
			response.add(directionSwell);
		} else {
			directionSwell = info.get(0).toString().substring(indexDirection + 10, indexDirection + 16);
			response.add(directionSwell);
		}
		
		if (info.get(0).toString().substring(indexDirectionMin + 13, indexDirectionMin + 15).equals("0,")) {
			directionSwellMin = info.get(0).toString().substring(indexDirectionMin + 13, indexDirectionMin + 14);
			response.add(directionSwellMin);
		} else {
			directionSwellMin = info.get(0).toString().substring(indexDirectionMin + 13, indexDirectionMin + 19);
			response.add(directionSwellMin);
		}
		return response;
	}
	
	/**
	 * 
	 * @param f
	 * @param g
	 * @return
	 */
	public static String toXY(float f, float g) {
		float vlong = f;
		float vlat = g;
		
		int x = long2tile(vlong, 9);
		int y = lat2tile(vlat, 9);
		
		return x + "/" + y;
		
	}
	
	public static int long2tile(float vlong, int zoom) {
        return (int) (Math.floor((vlong + 180) / 360 * Math.pow(2, zoom)));
	}
	
	public static int lat2tile(float vlat, int zoom) {
        return (int) (Math.floor((1 - Math.log(Math.tan(vlat * Math.PI / 180) + 1 / Math.cos(vlat * Math.PI / 180)) / Math.PI) / 2 * Math.pow(2, zoom)));
    }
	
	public static List getWeatherInfo(List info, long time) {
		
		String weather = "";
	    String differentIcon = "";
	    List response = new ArrayList();
	    String timeToCheck = Long.toString(time);
	    int indexTemp = info.get(0).toString().indexOf("temperature=");
	    int indexCond = info.get(0).toString().indexOf("condition=");
	    
	    for (int i = 0; i < info.size(); i++) {
			if (info.get(i).toString().substring(11, 21) == timeToCheck) {
				
				if (info.get(i).toString().substring(indexTemp + 12, indexTemp + 14).equals("0,")) {
					weather = info.get(i).toString().substring(indexTemp + 12, indexTemp + 13);
					response.add(weather);
				} else {
					weather = info.get(i).toString().substring(indexTemp + 12, indexTemp + 17);
					response.add(weather);
				}
				
				differentIcon = info.get(i).toString().substring(indexCond + 10, info.get(i).toString().length() - 1);
				response.add(differentIcon);
				
				return response;
			}
	    }
	    
	    if (info.get(0).toString().substring(indexTemp + 12, indexTemp + 14).equals("0,")) {
	    	weather = info.get(0).toString().substring(indexTemp + 12, indexTemp + 13);
			response.add(weather);
	    } else {
	    	weather = info.get(0).toString().substring(indexTemp + 12, indexTemp + 17);
			response.add(weather);
	    }
	    
		differentIcon = info.get(0).toString().substring(indexCond + 10, info.get(0).toString().length() - 1);
		response.add(differentIcon);
		
		return response;
	}
	
	public static String getTideInfo(List info, long time) {
		String tide = "";
		String timeToCheck = Long.toString(time);
		int indexHeight = info.get(0).toString().indexOf("height=");
		
		for (int i = 0; i < info.size(); i++) {
			if (info.get(i).toString().substring(11, 21) == timeToCheck) {
				tide = info.get(i).toString().substring(indexHeight + 8, info.get(i).toString().length() - 1);
				
				return tide;
			}
		}
		
		tide = info.get(0).toString().substring(indexHeight + 8, info.get(0).toString().length() - 1);
		return tide;
	}
	
	public static List getWindInfo(List info, long time) {
		List response = new ArrayList();
		String wind = "";
		String windText = "";
		String windDirection = "";
		String timeToCheck = Long.toString(time);
		int indexSpeed = info.get(0).toString().indexOf("speed=");
		int indexDirectionType = info.get(0).toString().indexOf("directionType=");
		int indexDirection = info.get(0).toString().indexOf("direction=");
		int indexGust = info.get(0).toString().indexOf(", gust=");
		
		for (int i = 0; i < info.size(); i++) {
			if (info.get(i).toString().substring(11, 21) == timeToCheck) {
				
				if (info.get(i).toString().substring(indexSpeed + 6, indexSpeed + 8).equals("0,")) {
					wind =  info.get(i).toString().substring(indexSpeed + 6, indexSpeed + 7);
					response.add(wind);
				} else {
					wind =  info.get(i).toString().substring(indexSpeed + 6, indexSpeed + 11);
					response.add(wind);
				}
				
				windText = info.get(i).toString().substring(indexDirectionType + 14, indexGust);
				response.add(windText);
				
				windDirection = info.get(i).toString().substring(indexDirection + 10, indexDirectionType - 2);
				response.add(windDirection);
				
				return response;
			}
		}
		
		if (info.get(0).toString().substring(indexSpeed + 6, indexSpeed + 8).equals("0,")) {
			wind =  info.get(0).toString().substring(indexSpeed + 6, indexSpeed + 7);
			response.add(wind);
		} else {
			wind =  info.get(0).toString().substring(indexSpeed + 6, indexSpeed + 11);
			response.add(wind);
		}
		
		windText = info.get(0).toString().substring(indexDirectionType + 14, indexGust);
		response.add(windText);
		
		windDirection = info.get(0).toString().substring(indexDirection + 10, indexDirectionType - 2);
		response.add(windDirection);
		
		return response;
	}

}
