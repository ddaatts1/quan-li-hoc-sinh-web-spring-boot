package com.example.HelloWorld.helloWorld.repository;

import com.example.HelloWorld.helloWorld.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<Class,Integer> {

    @Query(value = "SELECT u.* FROM Class u WhERE u.teacher_id = ?1",nativeQuery = true)
     List<Class> findByTecherUser_id(int teacherid);
}
