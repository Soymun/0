package com.example.universityservice.mapper;

import com.example.universityservice.dto.university.UniversityCreateDto;
import com.example.universityservice.dto.university.UniversityDto;
import com.example.universityservice.entity.University;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UniversityMapper {

    UniversityDto universityToUniversityDto(University university);

    University universityCreateDtoToUniversity(UniversityCreateDto universityCreateDto);
}
