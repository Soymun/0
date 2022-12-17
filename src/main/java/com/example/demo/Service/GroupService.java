package com.example.demo.Service;

import com.example.demo.DTO.GroupDto;
import com.example.demo.Entity.Group;

public interface GroupService {

    GroupDto getGroupById(Long id);

    GroupDto getGroupByName(String name);

    GroupDto updateGroup(GroupDto groupDto);

    void deleteGroup(Long id);

    GroupDto saveGroup(GroupDto groupDto);
}
