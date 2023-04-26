package com.example.lessonservice.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class Teacher {

    private Long id;
    private String name;
    private String nameTeacher;
    private String surnameTeacher;
    private String patronymicTeacher;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(id, teacher.id) && Objects.equals(name, teacher.name) && Objects.equals(nameTeacher, teacher.nameTeacher) && Objects.equals(surnameTeacher, teacher.surnameTeacher) && Objects.equals(patronymicTeacher, teacher.patronymicTeacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nameTeacher, surnameTeacher, patronymicTeacher);
    }
}
