package com.example.demo.models;

import com.example.demo.models.UserModel;

import javax.persistence.*;


@Entity
@Table(name = "favorites")
public class FavoritesBeachesModel {

	@Id
	private String idBeach;

	private String nameBeach;
	
	public String getIdBeach() {
		return idBeach;
	}

	public void setIdBeach(String idBeach) {
		this.idBeach = idBeach;
	}

	public String getNameBeach() {
		return nameBeach;
	}

	public void setNameBeach(String nameBeach) {
		this.nameBeach = nameBeach;
	}


}
