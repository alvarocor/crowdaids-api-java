package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.FavoritesBeachesModel;

public interface FavoritesBeachesRepository extends JpaRepository<FavoritesBeachesModel, String> {

}
