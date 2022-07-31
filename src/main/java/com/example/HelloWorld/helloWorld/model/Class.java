package com.example.HelloWorld.helloWorld.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "class")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int class_id;
    int size;
    Status status;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    User teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    Subject subject;

    @OneToMany(mappedBy = "aClass")
    List<Transcript> transcriptList;

    public Class(User teacher, Subject subject) {
        size =0;
        status=Status.DANG_DANG_KI;
        this.teacher = teacher;
        this.subject = subject;
    }

    public void updateSize(){
        this.size++;
    }
}
