package com.example.demo.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
		List<FavoritesBeachesModel> response = new ArrayList<FavoritesBeachesModel>();
		List<FavoritesBeachesModel> favorites = new ArrayList<FavoritesBeachesModel>();

		String query = "from FavoritesBeachesModel where id_surfline = '" + beach.getIdSurfline()+ "'";
		@SuppressWarnings("unchecked")
		List <FavoritesBeachesModel> list = entityManager.createQuery(query)
				.getResultList();

		UserModel user = userImplementations.getUser(userId);

		response.add(beach);

		if(!user.getFavorites().isEmpty()) {
			for (int i = 0; i < user.getFavorites().toArray().length; i++) {

				if(user.getFavorites().get(i).getIdSurfline().equals(beach.getIdSurfline())) {

					final long idBeach = user.getFavorites().get(i).getIdBeach();

					for (int index = 0; index < list.size(); index++) {
						if (list.get(index).getIdBeach().equals(user.getFavorites().get(i).getIdBeach())) {
							flushAndClear();
							FavoritesBeachesModel favToDelete = entityManager.find(FavoritesBeachesModel.class, idBeach);

							entityManager.remove(favToDelete);
							flushAndClear();
							return;
						}	
					}
				}
				favorites.add(user.getFavorites().get(i));
			}
			favorites.add(response.get(0));
			user.setFavorites(favorites);
		} else {
			user.setFavorites(response);		
		}

		entityManager.persist(response.get(0));
		return;
	}

	public List<FavoritesBeachesModel> getFavBeaches() {

		String query = "from FavoritesBeachesModel";
		@SuppressWarnings("unchecked")
		List <FavoritesBeachesModel> list = entityManager.createQuery(query)
				.getResultList();

		if (list.isEmpty())  {
			return new ArrayList<FavoritesBeachesModel>();
		} else {
			return list;
		}

	}

	public List<FavoritesBeachesModel> getMostFavBeaches() {

		List<FavoritesBeachesModel> mostFavorites = new ArrayList<FavoritesBeachesModel>();
		List <FavoritesBeachesModel>favorit = null;

		String query = "from FavoritesBeachesModel";
		@SuppressWarnings("unchecked")
		List <FavoritesBeachesModel> favorites = entityManager.createQuery(query)
				.getResultList();

		if (favorites.isEmpty())  {
			return new ArrayList<FavoritesBeachesModel>();
		} else {

			for (int i = 0; i < favorites.size(); i++) {
				int count = 0;

				for (int beach = 0; beach < favorites.size(); beach++) {

					if (favorites.get(i).getIdSurfline().equals(favorites.get(beach).getIdSurfline() )) {
						count++;
					}
					
					if (count >= 3) {
						int index = i;
						mostFavorites.add(favorites.get(i));
						
						List <FavoritesBeachesModel> favsToCheck = favorites;
						favorit = favsToCheck.stream()
								.filter(beach1 -> !beach1.getIdSurfline().equals(favsToCheck.get(index).getIdSurfline()))
								.collect(Collectors.toList());
						beach = favorites.size() + 1;
						favorites = favorit;
					}
				}
			}
			return mostFavorites;
		}
	}
	
	private void flushAndClear() {
		entityManager.flush();
		entityManager.clear();
	}
}
