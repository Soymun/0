package com.example.courseservice.dto.geoupcourse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupCourseCreateDto {

    private Long groupId;

    private Long courseId;
}
