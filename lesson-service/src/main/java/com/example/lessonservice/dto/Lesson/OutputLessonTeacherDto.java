package com.example.lessonservice.dto.Lesson;

import com.example.lessonservice.dto.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputLessonTeacherDto {

    private OutputLessonDto outputLessonDto;

    private List<Group> groups;
}
