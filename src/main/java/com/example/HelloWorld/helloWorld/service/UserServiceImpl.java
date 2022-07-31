package com.example.HelloWorld.helloWorld.service;

import com.example.HelloWorld.helloWorld.model.User;
import com.example.HelloWorld.helloWorld.repository.UserRepository;
import com.example.HelloWorld.helloWorld.userconfig.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getuser(){
        return userRepository.findAll();
    }

    @Override
    public User getUserByName(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public void updateUser(User user){
        User updateUser = userRepository.findByUsername(user.getUsername());
        updateUser.setUsername(user.getUsername());
        updateUser.setAddress(user.getAddress());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        if (user.getImage()!=null)
        updateUser.setImage(user.getImage());
        userRepository.save(updateUser);
    }

    @Override
    public void updatePassword(String password){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user= userRepository.findByUsername(authentication.getName());
        user.setPassword(password);
        userRepository.save(user);
    }
}
