package com.example.demo.Mappers;


import com.example.demo.DTO.MarksDto;
import com.example.demo.Entity.Marks;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarksMapper {

    Marks marksDtoToMarks(MarksDto marksDto);

    MarksDto marksToMarksDto(Marks marks);
}
