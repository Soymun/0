package com.example.courseservice.service;

import com.example.courseservice.dto.marks.MarkCreateDto;
import com.example.courseservice.dto.marks.MarksDto;
import com.example.courseservice.dto.marks.MarksUpdateDto;

import java.util.List;

public interface MarksService {

    void saveMark(MarkCreateDto marksDto);

    MarksDto updateMarks(MarksUpdateDto marksDto);

    MarksDto getMarksById(Long id);

    List<MarksDto> getMarksByUserId(Long id);

    void deleteMarks(Long id);

    List<MarksDto> getMarksByCoursesAndGroup(Long coursesId, List<Long> studentIds);
}
