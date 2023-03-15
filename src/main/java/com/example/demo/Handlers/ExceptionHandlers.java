package com.example.demo.Handlers;

import com.example.demo.Response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlers {

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<?> runtime(RuntimeException e){
//        return ResponseEntity.ok(ResponseDto.builder().error(e.getMessage()).build());
//    }
//
//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<?> noSuchElements(NoSuchElementException e){
//        return ResponseEntity.ok(ResponseDto.builder().error("Ничего не было найдено").build());
//    }
}
