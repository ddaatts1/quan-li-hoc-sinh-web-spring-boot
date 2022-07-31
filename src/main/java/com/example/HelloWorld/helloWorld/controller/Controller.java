package com.example.HelloWorld.helloWorld.controller;


import com.example.HelloWorld.helloWorld.model.User;
import com.example.HelloWorld.helloWorld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.ProcessBuilder.Redirect;

import javax.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index(){
        return "redirect:/login";
    }


    @GetMapping("/getUser")
    public String currentUser(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(authentication.getName());
        session.setAttribute(authentication.getName(), user);
        if(user.getRole().equals("STUDENT")){
            return "redirect:/student";
        }
        return "redirect:/teacher";
        
    }
}
