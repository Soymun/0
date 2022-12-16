package com.example.demo.SaveFromFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NativeLesson {

    private String group;
    private  String lesson;
    private Long number;

    private Long weak;

    private String teacher;

    private String classroom;

    private String type;

    private String day;

    public NativeLesson(String group, String lesson, Long number, String day) {
        this.group = group;
        this.lesson = lesson;
        this.number = number;
        this.day = day;
    }

    public NativeLesson(NativeLesson nativeLesson) {
        this.group = nativeLesson.group;
        this.lesson = nativeLesson.lesson;
        this.number = nativeLesson.number;
        this.day = nativeLesson.day;
    }
}
