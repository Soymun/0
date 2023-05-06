package com.example.courseservice.mappers;


import com.example.courseservice.dto.marks.MarkCreateDto;
import com.example.courseservice.dto.marks.MarksDto;
import com.example.courseservice.entity.Marks;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarksMapper {

    Marks marksDtoToMarks(MarkCreateDto marksDto);

    MarksDto marksToMarksDto(Marks marks);
}
