package com.example.universityservice.mapper;

import com.example.universityservice.dto.classroom.ClassRoomCreateDto;
import com.example.universityservice.dto.classroom.ClassRoomDto;
import com.example.universityservice.entity.ClassRoom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassRoomMapper {

    ClassRoom classRoomCreateDtoToClassRoom(ClassRoomCreateDto classRoomCreateDto);

    ClassRoomDto classRoomToClassRoomCreateDto(ClassRoom classRoom);
}
