package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;


@Entity
@Table(name = "favorites")
public class FavoritesBeachesModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "beach_id")
	private Long idBeach;
	
	@Column(name = "beach_name")
	private String nameBeach;
	
	@Column(name = "id_surfline")
	private String idSurfline;

	public Long getIdBeach() {
		return idBeach;
	}

	//public void setIdBeach(String idBeach) {
	//	this.idBeach = idBeach;
	//}

	public String getNameBeach() {
		return nameBeach;
	}

	public void setNameBeach(String nameBeach) {
		this.nameBeach = nameBeach;
	}
	
	public String getIdSurfline() {
		return idSurfline;
	}

	public void setIdSurfline(String idSurfline) {
		this.idSurfline = idSurfline;
	}
}
