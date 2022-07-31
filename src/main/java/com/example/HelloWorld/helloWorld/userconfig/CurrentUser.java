package com.example.HelloWorld.helloWorld.userconfig;

import com.example.HelloWorld.helloWorld.model.User;
import com.example.HelloWorld.helloWorld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {
    @Autowired
    UserService userService;

    public User  getCurrentUSer(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserByName(authentication.getName());
    }

    public String getCurrnetUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
