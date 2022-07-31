package com.example.HelloWorld.helloWorld.service;


import com.example.HelloWorld.helloWorld.model.CustomUserDetail;
import com.example.HelloWorld.helloWorld.model.User;
import com.example.HelloWorld.helloWorld.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailSevice implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("ko tim thay user");
        }

        return new CustomUserDetail(user);
    }
}
