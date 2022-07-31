package com.example.HelloWorld.helloWorld.repository;

import com.example.HelloWorld.helloWorld.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Integer> {
}
