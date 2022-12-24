package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class GetUpdateLessonDto {

    private List<Long> ids;

    private String lesson;

    private LocalDateTime fromTime;

    private LocalDateTime toTime;

    private Long number;

    private String teacherName;

    private String classRoom;

    private List<Long> weeks;

    private String type;

    public GetUpdateLessonDto() {
        this.ids = new ArrayList<>();
        this.weeks = new ArrayList<>();
    }
}
