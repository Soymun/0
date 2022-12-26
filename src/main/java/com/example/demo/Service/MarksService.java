package com.example.demo.Service;

import com.example.demo.DTO.MarksDto;

import java.util.List;

public interface MarksService {

    MarksDto saveMark(MarksDto marksDto);

    MarksDto updateMarks(MarksDto marksDto);

    MarksDto getMarksById(Long id);

    List<MarksDto> getMarksByUserId(Long id);

    void deleteMarks(Long id);
}
