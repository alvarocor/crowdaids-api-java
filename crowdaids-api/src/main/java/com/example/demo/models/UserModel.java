package com.example.demo.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.example.demo.repositories.FavoritesImplementations;

import javax.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    private String fullname;
    private String username;
    private String email;
    private String password;
    private String oldPassword;
    
    @OneToMany(targetEntity = FavoritesBeachesModel.class, cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private List <FavoritesBeachesModel> favorites;

	public List<FavoritesBeachesModel> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<FavoritesBeachesModel> favorites) {
		this.favorites = favorites;
	}

	public UUID getId() { return id; }

    public void setId(UUID id)  { this.id = id; }

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