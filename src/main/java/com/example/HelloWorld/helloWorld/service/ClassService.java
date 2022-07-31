package com.example.HelloWorld.helloWorld.service;

import com.example.HelloWorld.helloWorld.model.Class;

import java.util.List;

public interface ClassService {
    List<Class> getAllClass();

    void addClass(Class c);

    List<Class> getClassById(int techerid);

    Class getClassByClassId(int classid);

    void closeCLass(int ClassId);

    void updateClassSize(int classid);
}
