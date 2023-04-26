package com.example.lessonservice.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class Group {

    private Long id;

    private String group;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group1 = (Group) o;
        return Objects.equals(id, group1.id) && Objects.equals(group, group1.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group);
    }
}
