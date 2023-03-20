package com.example.demo.Service;

import com.example.demo.DTO.Group.GroupDto;
import com.example.demo.Entity.Group;

import java.util.List;

public interface GroupService {

    GroupDto getGroupById(Long id);

    Group getGroupByName(String name);

    GroupDto updateGroup(GroupDto groupDto);

    void deleteGroup(Long id);

    GroupDto saveGroup(GroupDto groupDto);

    List<GroupDto> getGroups(Long page);
}
