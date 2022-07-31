package com.example.HelloWorld.helloWorld.service;

import com.example.HelloWorld.helloWorld.model.Subject;
import com.example.HelloWorld.helloWorld.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    @Override
    public List<Subject> getList() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject getById(int subjectid) {
        return subjectRepository.getById(subjectid);
    }
}
