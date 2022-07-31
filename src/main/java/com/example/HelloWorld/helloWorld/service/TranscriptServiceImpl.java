package com.example.HelloWorld.helloWorld.service;

import com.example.HelloWorld.helloWorld.model.ListTranscript;
import com.example.HelloWorld.helloWorld.model.Transcript;
import com.example.HelloWorld.helloWorld.repository.TranscriptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TranscriptServiceImpl implements TranscriptService{

    @Autowired
    TranscriptRepository transcriptRepository;

    @Override
    public void addTranscript(Transcript transcript){
        transcriptRepository.save(transcript);
    }

    @Override
    public List<Transcript> findByUser_id(int userid){
        return transcriptRepository.findByUser_id(userid);
    }

    @Override
    public List<Transcript> findByClass_id(int classid){
        return transcriptRepository.findByClass_id(classid);
    }

    @Override
    public void submitTranscript(ListTranscript listTranscript, int classid){
        List<Transcript> list = transcriptRepository.findByClass_id(classid);
        AtomicInteger i= new AtomicInteger(0);
        list.stream().map(t ->{
            System.out.println(i);
            t.setMark1(listTranscript.getList().get(i.get()).getMark1());
            t.setMark2(listTranscript.getList().get(i.get()).getMark2());
            i.getAndIncrement();
            return t;
        }).forEach(t -> transcriptRepository.save(t));

    }
}
