package com.example.lessonservice.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class Group {

    private Long id;

    private Long universityId;

    private String group;

    private Long numberCourse;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group1 = (Group) o;
        return Objects.equals(id, group1.id)&& Objects.equals(numberCourse, group1.numberCourse) && Objects.equals(group, group1.group) && Objects.equals(universityId, group1.universityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group);
    }
}
