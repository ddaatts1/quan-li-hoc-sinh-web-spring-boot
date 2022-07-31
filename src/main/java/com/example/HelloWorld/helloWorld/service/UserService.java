package com.example.HelloWorld.helloWorld.service;

import com.example.HelloWorld.helloWorld.model.User;

import java.util.List;

public interface UserService {
    List<User> getuser();

    User getUserByName(String username);

    void updateUser(User user);

    void updatePassword(String password);
}
