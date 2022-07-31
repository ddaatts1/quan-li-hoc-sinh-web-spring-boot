package com.example.HelloWorld.helloWorld.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler({ BindException.class})
//    public ResponseEntity<String> handleExceptionA(Exception e) {
//        System.out.println("so lieu khong hop le");
//        return ResponseEntity.status(432).body("so lieu khong hop le");
//    }

}
