package com.example.ProjectExam.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HeroNotFoundException extends RuntimeException {
    public HeroNotFoundException(String s) {
        super(s);
    }


}
