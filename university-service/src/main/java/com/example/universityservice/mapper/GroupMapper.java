package com.example.universityservice.mapper;



import com.example.universityservice.dto.group.GroupCreateDto;
import com.example.universityservice.dto.group.GroupDto;
import com.example.universityservice.entity.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    Group groupDtoToGroup(GroupCreateDto groupDto);

    GroupDto groupToGroupDto(Group group);
}
