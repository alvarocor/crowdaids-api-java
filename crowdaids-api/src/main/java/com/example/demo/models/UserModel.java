package com.example.demo.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "user")
public class UserModel {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID id;
	
	@Column(name = "fullname")
    private String fullname;
	
	@Column(name = "username")
    private String username;
	
	@Column(name = "email")
    private String email;
	
	@Column(name = "password")
    private String password;
	
	@Column(name = "old_password")
    private String oldPassword;

	@OneToMany(targetEntity = FavoritesBeachesModel.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private List <FavoritesBeachesModel> favorites;

	public List<FavoritesBeachesModel> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<FavoritesBeachesModel> favorites) {
		this.favorites = favorites;
	}

	public UUID getId() { return id; }

    //public void setId(UUID id)  { this.id = id; }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() { return oldPassword;}

    public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword;}
    
}