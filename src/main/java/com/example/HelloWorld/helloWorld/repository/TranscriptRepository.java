package com.example.HelloWorld.helloWorld.repository;

import com.example.HelloWorld.helloWorld.model.Transcript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranscriptRepository extends JpaRepository<Transcript,Integer> {

    @Query(value = "SELECT t.* FROM Transcript t WhERE t.user_id = ?1",nativeQuery = true)
    List<Transcript> findByUser_id(int userid);
    @Query(value = "SELECT t.* FROM Transcript t WhERE t.class_id = ?1",nativeQuery = true)
    List<Transcript> findByClass_id(int classid);

}
