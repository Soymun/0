package com.example.universityservice.service;


import com.example.universityservice.dto.group.GroupCreateDto;
import com.example.universityservice.dto.group.GroupDto;
import com.example.universityservice.dto.group.GroupUpdateDto;
import com.example.universityservice.entity.Group;

import java.util.List;

public interface GroupService {

    GroupDto getGroupById(Long id);

    Group getGroupByName(String name);

    GroupDto updateGroup(GroupUpdateDto groupDto);

    void deleteGroup(Long id);

    GroupDto saveGroup(GroupCreateDto groupDto);

    List<GroupDto> getGroups(Long page);
}
