package com.example.demo.repositories;

import com.example.demo.models.UserModel;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository {

    @Transactional
    UserModel getUser(String id);

    void delete(String id, UserModel password);

    void register(UserModel user);

    UserModel authenticateUser(UserModel user);

    void modifyUser(UserModel user1, UserModel newData);
}