package com.example.demo.Mappers;


import com.example.demo.DTO.GroupDto;
import com.example.demo.Entity.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    Group groupDtoToGroup(GroupDto groupDto);

    GroupDto groupToGroupDto(Group group);
}
