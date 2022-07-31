package com.example.HelloWorld.helloWorld.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Entity
@Table(name = "transcript")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transcript {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int transcript_id;

    @ManyToOne
    @JoinColumn(name = "class_id")
    Class aClass;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User student;
    @Max(value = 10,message = "điểm không được quá 10!")
     @Min(value = 0,message = "điểm không được dưới 0!")
    int mark1;

    @Max(value = 10,message = "điểm không được quá 10!")
    @Min(value = 0,message = "điểm không được dưới 0!")
    int mark2;

    public Transcript(User user, int classid) {
        this.student = user;
        aClass = new Class();
        aClass.setClass_id(classid);
    }
}
