package com.example.HelloWorld.helloWorld.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListTranscript {
    @Valid
    List< Transcript> list;
}
