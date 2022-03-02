package com.example.demo.repositories;

import com.example.demo.models.UserModel;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {

    List<UserModel> getUsers();

    void delete(Long id);

    void register(UserModel user);

    UserModel authenticateUser(UserModel user);
}