package com.example.HelloWorld.helloWorld.service;

import com.example.HelloWorld.helloWorld.model.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> getList();

    Subject getById(int subjectid);
}
