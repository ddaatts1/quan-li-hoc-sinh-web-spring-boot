package com.example.HelloWorld.helloWorld.service;

import com.example.HelloWorld.helloWorld.model.ListTranscript;
import com.example.HelloWorld.helloWorld.model.Transcript;

import java.util.List;

public interface TranscriptService {
    void addTranscript(Transcript transcript);

    List<Transcript> findByUser_id(int userid);

    List<Transcript> findByClass_id(int classid);

    void submitTranscript(ListTranscript listTranscript, int classid);
}
