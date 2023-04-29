package com.example.lessonservice.file;

public enum Odds {
    EVEN(1), ODD(2), NONE(0);

    final int num;

    Odds(int num) {
        this.num = num;
    }
}
