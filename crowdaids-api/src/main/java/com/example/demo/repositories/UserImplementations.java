package com.example.demo.repositories;

import com.example.demo.models.UserModel;
import com.example.demo.utils.JWT;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository
@Transactional
    
public class UserImplementations implements UserRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public UserModel getUser(String id) {
        String query = "from UserModel where id = '" + id + "'";
        List <UserModel> list = entityManager.createQuery(query)
                //.setParameter("id", idUser)
                .getResultList();

        if (list.isEmpty()) {
            throw new Error("User with " + id + " not found.");
        }
        return list.get(0);
    }

    @Override
    public void delete(String id, UserModel password) {
        String query = "from UserModel where id = '" + id + "'";
        List <UserModel> user = entityManager.createQuery(query)
                //.setParameter("id", idUser)
                .getResultList();

        if (user.isEmpty()) {
            throw new Error("User with " + id + " not found.");
        }

        if (user.get(0).getPassword().equals(password)) {
            entityManager.remove(user.get(0));
        } else {
            throw new Error("Wrong password");
        }
    }

    @Override
    public void register(UserModel user) {
        entityManager.merge(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public UserModel authenticateUser(UserModel userAuth) {
        String query = "from UserModel where username = :username";
        List <UserModel> list = entityManager.createQuery(query)
                .setParameter("username", userAuth.getUsername())
                .getResultList();

        if (list.isEmpty()) {
            throw new Error("User with username: " + userAuth.getUsername() + " not found.");
        }

        String passwordHashed = list.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(passwordHashed, userAuth.getPassword())) {
            return list.get(0);
        } else {
            throw new Error("Wrong credentials.");
        }
    }

    @Override
    public void modifyUser(UserModel user1, UserModel newData) {

        if (user1 == null) throw new Error("user with id " + user1.getId() + "not found");

        if (newData.getPassword() != null) {
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            if (!argon2.verify(user1.getPassword(), ((UserModel) newData).getOldPassword())) {
                throw new Error("Wrong credentials");
            } else {
                ((UserModel) newData).setOldPassword(null);
            }

            if(!newData.getPassword().equals(null)) {
                String hash = argon2.hash(1, 1024, 1, newData.getPassword());
                user1.setPassword(hash);
            }
        }
        if(newData.getUsername() != null) {
            user1.setUsername(newData.getUsername());
        }
        if(newData.getEmail() != null) {
            user1.setEmail(newData.getEmail());
        }
        if(newData.getFullname() != null) {
            user1.setFullname(newData.getFullname());
        }
    }

}