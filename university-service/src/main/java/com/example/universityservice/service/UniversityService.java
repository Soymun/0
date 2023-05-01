package com.example.universityservice.service;

import com.example.universityservice.dto.university.UniversityCreateDto;
import com.example.universityservice.dto.university.UniversityDto;

import java.util.List;

public interface UniversityService {

    UniversityDto getUniversityById(Long id);

    void saveUniversity(UniversityCreateDto universityCreateDto);

    UniversityDto updateUniversity(UniversityDto universityDto);

    void deleteUniversityById(Long id);

    List<UniversityDto> getAllUniversities();
}
