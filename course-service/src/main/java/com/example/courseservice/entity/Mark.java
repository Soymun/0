package com.example.courseservice.entity;

public enum Mark {
    NONE(""), ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), OFFSET("Зачет"), NOOFFSET("Незачёт");

    private String p;

    Mark(String p) {
        this.p = p;
    }
}
