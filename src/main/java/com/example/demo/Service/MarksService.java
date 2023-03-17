package com.example.demo.Service;

import com.example.demo.DTO.Marks.MarkCreateDto;
import com.example.demo.DTO.Marks.MarksDto;
import com.example.demo.DTO.Marks.MarksUpdateDto;

import java.util.List;

public interface MarksService {

    void saveMark(MarkCreateDto marksDto);

    MarksDto updateMarks(MarksUpdateDto marksDto);

    MarksDto getMarksById(Long id);

    List<MarksDto> getMarksByUserId(Long id);

    void deleteMarks(Long id);

    List<MarksDto> getMarksByCoursesAndGroup(Long coursesId, Long groupId);
}
