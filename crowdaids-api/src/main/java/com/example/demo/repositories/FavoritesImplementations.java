package com.example.demo.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.FavoritesBeachesModel;
import com.example.demo.models.UserModel;

@Repository
@Transactional

public class FavoritesImplementations {
	
	@Autowired
	 private UserImplementations userImplementations;
	
	@PersistenceContext
    EntityManager entityManager;

	public void toggleFavBeach(String userId, FavoritesBeachesModel beach) {
		List response = new ArrayList();
		List favorites = new ArrayList();

		String query = "from FavoritesBeachesModel where user_id = '" + userId + "' and idBeach = '" + beach.getIdBeach() + "'";
		List <FavoritesBeachesModel> list = entityManager.createQuery(query)
				.getResultList();

		if (list.isEmpty()) {
			UserModel user1 = userImplementations.getUser(userId);

			response.add(beach);

			if(!user1.getFavorites().isEmpty()) {
				for (int i = 0; i < user1.getFavorites().toArray().length; i++) {
					
					favorites.add(user1.getFavorites().get(i));
				}
				//favorites.add(user1.getFavorites().toArray());
				favorites.add(response.get(0));
				user1.setFavorites(favorites);
			} else {
				user1.setFavorites(response);
			}

			entityManager.merge(response.get(0));

		} else {
			entityManager.remove(list.get(0));
		}
	}
	
	public List getFavBeaches(String userId) {
		
		String query = "from FavoritesBeachesModel where user_id = '" + userId + "'";
		List <FavoritesBeachesModel> list = entityManager.createQuery(query)
				.getResultList();
		
		if (list.isEmpty())  {
			return new ArrayList();
		} else {
			return list;
		}
		
	}
}
