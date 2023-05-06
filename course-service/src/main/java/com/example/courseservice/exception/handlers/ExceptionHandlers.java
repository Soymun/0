package com.example.courseservice.exception.handlers;

import com.example.courseservice.exception.NotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlers {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> notFound(NotFoundException exception){
        log.debug(exception.getMessage());
        return ResponseEntity.status(404).body(exception.getMessage());
    }

    @ExceptionHandler({NoResultException.class})
    public ResponseEntity<?> noResult(NoResultException exception){
        log.debug(exception.getMessage());
        return ResponseEntity.ok("Данные не найдены");
    }

    @ExceptionHandler({NonUniqueResultException.class})
    public ResponseEntity<?> noUnique(NonUniqueResultException exception){
        log.debug(exception.getMessage());
        return ResponseEntity.ok("Данные не уникальны");
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> runtime(RuntimeException exception){
        log.debug(exception.getMessage());
        return ResponseEntity.ok("Упс что-то случилось");
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> exception(Exception exception){
        log.debug(exception.getMessage());
        return ResponseEntity.ok("Случилось что-то ужасное, попробуйте позже");
    }
}
