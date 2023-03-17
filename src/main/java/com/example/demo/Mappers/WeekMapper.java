package com.example.demo.Mappers;

import com.example.demo.DTO.Week.WeekCreateDto;
import com.example.demo.DTO.Week.WeekDto;
import com.example.demo.Entity.Week;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeekMapper {

    Week weekCreateDtoToWeek(WeekCreateDto weekCreateDto);

    WeekDto weekToWeekDto(Week week);
}
