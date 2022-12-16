package com.example.demo.Entity;

public enum Permission {

    USER("USER"), TEACHER("TEACHER"), ADMIN("ADMIN");

    final String p;

    Permission(String p) {
        this.p = p;
    }
}
