package com.example.universityservice.entity;

public enum Status {
    CREATED("CREATED"), DOING("DOING"), FINISH("FINISH");
    final String p;

    Status(String p) {
        this.p = p;
    }
}
