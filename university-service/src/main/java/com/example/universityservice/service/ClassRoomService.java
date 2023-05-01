package com.example.universityservice.service;

import com.example.universityservice.dto.classroom.ClassRoomCreateDto;
import com.example.universityservice.dto.classroom.ClassRoomDto;
import com.example.universityservice.dto.classroom.ClassRoomUpdateDto;

import java.util.List;

public interface ClassRoomService {

    ClassRoomDto getClassRoomById(Long id);

    ClassRoomDto getClassRoomByNameAndUniversityId(String name, Long universityId);

    void saveClassRoom(ClassRoomCreateDto classRoomCreateDto);

    void deleteClassRoom(Long id);

    ClassRoomDto updateClassRoom(ClassRoomUpdateDto classRoomUpdateDto);

    List<ClassRoomDto> getClassRoomByUniversityId(Long universityId);
}
