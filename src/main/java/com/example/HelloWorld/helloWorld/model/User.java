package com.example.HelloWorld.helloWorld.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int user_id;
    @Column(name = "user_name")
    String username;
    String password;
    String user_full_name;
    String role;
    String address;
    int phone;
    String email;
    String image;

    @Transient
    public String getImagePath(){
        String path = "/image/"+image;

        return path;
    }

    @OneToMany(mappedBy = "teacher",fetch = FetchType.LAZY)
    List<Class> classList;

    @OneToMany(mappedBy = "student",fetch = FetchType.LAZY)
    List<Transcript> transcriptList;

    public void setUser(User user) {
        username = user.getUsername();
        password = user.getPassword();
        user_id = user.getUser_id();
        user_full_name = user.getUser_full_name();
        role = user.getRole();
    }
}
