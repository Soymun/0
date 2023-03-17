package com.example.demo.Mappers;


import com.example.demo.DTO.Marks.MarkCreateDto;
import com.example.demo.DTO.Marks.MarksDto;
import com.example.demo.Entity.Marks;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarksMapper {

    Marks marksDtoToMarks(MarkCreateDto marksDto);

    MarksDto marksToMarksDto(Marks marks);
}
