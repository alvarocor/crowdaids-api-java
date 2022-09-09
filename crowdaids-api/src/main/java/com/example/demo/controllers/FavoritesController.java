package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.FavoritesBeachesModel;
import com.example.demo.repositories.FavoritesImplementations;
import com.example.demo.utils.JWT;
import com.example.demo.utils.Validators;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class FavoritesController {
	
	@Autowired
	private FavoritesImplementations favoritesImplementations;
	
	@Autowired
	private JWT jwt;

	@RequestMapping(value = "/api/users/favorites", method = RequestMethod.PATCH)
	public void toggleFavBeach (@RequestHeader(value = "Authorization") String token,
					@RequestBody FavoritesBeachesModel beach) {
			Validators.validateToken(token);

	        String bearerToken = token;
	        String[] parts = bearerToken.split(" ");
	        String finalToken = parts[1];

	        String userId = validateToken(finalToken);
	        
	        favoritesImplementations.toggleFavBeach(userId, beach);  
	}
	
	@RequestMapping(value = "/api/users/favorites", method = RequestMethod.GET)
	public List getFavBeaches(@RequestHeader(value = "Authorization") String token) {
		Validators.validateToken(token);
 
        List favorites = favoritesImplementations.getFavBeaches();
        
        return favorites;
	}
	
	@RequestMapping(value = "/api/users/mostFavorites", method = RequestMethod.GET)
	public List getMostFavBeaches() {
		
		List mostFavs = favoritesImplementations.getMostFavBeaches();
		
		return mostFavs;
	}
	
	 public String validateToken(String token) {
	        String userId = jwt.getKey(token);
	        return userId;
	    }
}
