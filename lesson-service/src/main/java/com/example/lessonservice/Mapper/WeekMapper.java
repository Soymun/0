package com.example.lessonservice.Mapper;


import com.example.lessonservice.dto.Week.WeekCreateDto;
import com.example.lessonservice.dto.Week.WeekDto;
import com.example.lessonservice.entity.Week;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeekMapper {

    Week weekCreateDtoToWeek(WeekCreateDto weekCreateDto);

    WeekDto weekToWeekDto(Week week);
}
