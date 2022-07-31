package com.example.HelloWorld.helloWorld.service;

import com.example.HelloWorld.helloWorld.model.Class;
import com.example.HelloWorld.helloWorld.model.Status;
import com.example.HelloWorld.helloWorld.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    ClassRepository classRepository;

    @Override
    public List<Class> getAllClass() {
        return classRepository.findAll();
    }

    @Override
    public void addClass(Class c) {
        classRepository.save(c);
    }

    @Override
    public List<Class> getClassById(int techerid) {
        return classRepository.findByTecherUser_id(techerid);
    }

    @Override
    public Class getClassByClassId(int classid) {
        return classRepository.findById(classid).get();
    }

    @Override
    public void closeCLass(int ClassId) {
        Class closeclass = classRepository.getById(ClassId);
        closeclass.setStatus(Status.DANG_HOC);
        classRepository.save(closeclass);
    }

    @Override
    public void updateClassSize(int classid) {
        Class aclass = classRepository.findById(classid).get();
        aclass.updateSize();
        classRepository.save(aclass);
    }
}
